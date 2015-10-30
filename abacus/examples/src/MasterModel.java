package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import model.gizmos.Absorber;
import model.gizmos.GBall;
import model.gizmos.IGizmo;
import model.physics.Geometry;
import model.physics.Geometry.VectPair;
import model.physics.Vect;

public class MasterModel extends Observable implements IMasterModel, Observer {
    private IGameBoard board;
    private SavedBoard savedBoard;

    public MasterModel(IGameBoard board) {
        setBoard(board);
    }

    @Override
    public void setBoard(IGameBoard newBoard) {
        this.board = newBoard;
        board.asObservable().addObserver(this);
        saveCopyOfBoard(board);
        notifyChanged();
    }

    private void saveCopyOfBoard(IGameBoard board) {
        try {
            this.savedBoard = new SavedBoard(board);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IGameBoard getBoard() {
        return board;
    }

    @Override
    public Observable asObservable() {
        return this;
    }

    @Override
    public void tick(double tickTime) {
        List<IGizmo> gizmos = board.getGizmos();
        List<GBall> balls = board.getBalls();
        List<Absorber> absorbers = board.getAbsorbers();

        double timeAlreadyMoved = 0;

        while(timeAlreadyMoved < tickTime) {
            FutureCollision nextCollision = getNextCollision(balls, gizmos, absorbers);

            if(nextCollision != null && nextCollision.getTimeUntilCollision() + timeAlreadyMoved < tickTime) {
                // a collision will happen within the next tick
                double timeUntilCollision = nextCollision.getTimeUntilCollision();
                if(timeUntilCollision > 0) {
                    moveAllBallsAndGizmos(timeUntilCollision);
                    applyFriction(balls, timeUntilCollision);
                    applyGravity(balls, timeUntilCollision);
                }
                GBall ball = nextCollision.getCollidingBall();
                Vect newBallVelocity = null;
                switch(nextCollision.getCollisionType()) {
                    case BALL_LINE:
                        newBallVelocity = Geometry.reflectWall(
                                nextCollision.getLineBeingHit(),
                                ball.getVelocity(),
                                nextCollision.getCollidingObject().getCoefficientOfReflection());
                        break;
                    case BALL_ROTATING_LINE:
                        newBallVelocity = Geometry.reflectRotatingWall(
                                nextCollision.getLineBeingHit(),
                                nextCollision.getCenterOfRotation(),
                                nextCollision.getAngularVelocity(),
                                ball.getCircle(),
                                ball.getVelocity(),
                                nextCollision.getCollidingObject().getCoefficientOfReflection());
                        break;
                    case BALL_CIRCLE:
                        newBallVelocity = Geometry.reflectCircle(
                                nextCollision.getCircleBeingHit().getCenter(),
                                ball.getCircle().getCenter(),
                                ball.getVelocity(),
                                nextCollision.getCollidingObject().getCoefficientOfReflection());
                        break;
                    case BALL_ROTATING_CIRCLE:
                        newBallVelocity = Geometry.reflectRotatingCircle(
                                nextCollision.getCircleBeingHit(),
                                nextCollision.getCenterOfRotation(),
                                nextCollision.getAngularVelocity(),
                                ball.getCircle(), ball.getVelocity(),
                                nextCollision.getCollidingObject().getCoefficientOfReflection());
                        break;
                    case BALL_BALL:
                        GBall otherBall = nextCollision.getOtherBall();
                        VectPair velocities = Geometry.reflectBalls(
                                ball.getCircle().getCenter(),
                                ball.getMass(),
                                ball.getVelocity(),
                                otherBall.getCircle().getCenter(),
                                ball.getMass(),
                                otherBall.getVelocity());
                        newBallVelocity = velocities.v1;
                        otherBall.setVelocity(velocities.v2);
                        break;
                    case BALL_ABSORBER:
                        Absorber absorber = nextCollision.getAbsorber();
                        newBallVelocity = absorber.absorbBall(ball);
                        break;
                    default:
                        throw new RuntimeException("Don't know how to handle collision type " + nextCollision.getCollisionType());
                }
                ball.setVelocity(newBallVelocity);
                IGizmo collidedGizmo = nextCollision.getCollidingObject();
                if(collidedGizmo != null) {
                    board.triggerGizmo(collidedGizmo);
                }
                timeAlreadyMoved += timeUntilCollision;
            } else {
                // simulate for as long as is left in this tick
                double timeLeft = tickTime - timeAlreadyMoved;
                moveAllBallsAndGizmos(timeLeft);
                applyFriction(balls, timeLeft);
                applyGravity(balls, timeLeft);

                break;
            }
        }

        notifyChanged();
    }

    private FutureCollision getNextCollision(List<GBall> balls, List<IGizmo> gizmos, List<Absorber> absorbers) {
        FutureCollision nextCollision = FutureCollision.NO_COLLISION;

        List<GBall> movingBalls = new ArrayList<>();
        for(GBall ball: balls) {
            if(!ball.isStopped())
                movingBalls.add(ball);
        }
        for (IGizmo g : gizmos) {
            FutureCollision result = g.getNextCollision(movingBalls);
            if(result == null) {
                System.out.println(g);
            }
            if(nextCollision.compareTo(result) >= 0) {
                nextCollision = result;
            }
        }

        for (Absorber a : absorbers) {
            FutureCollision result = a.getNextCollision(movingBalls);
            if(nextCollision.compareTo(result) >= 0) {
                nextCollision = result;
            }
        }

        for(GBall ball : movingBalls) {
            // we'll check ball 1 and ball 2, then ball 2 and ball 1 - superfluous, but not a big deal
            for(GBall otherBall : balls) {
                double timeToCollide = Geometry.timeUntilBallBallCollision(ball.getCircle(), ball.getVelocity(),
                        otherBall.getCircle(), otherBall.getVelocity());
                FutureCollision result = new FutureCollision(ball, otherBall, timeToCollide);
                if(nextCollision.compareTo(result) >= 0) {
                    nextCollision = result;
                }
            }
        }

        return nextCollision;
    }

    private void moveAllBallsAndGizmos(double interval) {
        for(IGizmo g : board.getGizmos()) {
            g.update(interval);
        }
        for(GBall ball : board.getBalls()) {
            ball.update(interval);
        }
    }

    private void applyGravity(List<GBall> balls, double interval) {
        Vect gravVect = new Vect(0, board.getGravity() * interval);
        for(GBall ball : balls) {
            if(!ball.isStopped()) {
                Vect velocity = ball.getVelocity();
                ball.setVelocity(velocity.plus(gravVect));
            }
        }
    }

    private void applyFriction(List<GBall> balls, double interval) {
        for(GBall ball : balls) {
            if(!ball.isStopped()) {
                double vx = ball.getVelocity().x();
                double vy = ball.getVelocity().y();
                vx = vx * (1 - board.getMu() * interval - board.getMu2() * Math.abs(vx) * interval);
                vy = vy * (1 - board.getMu() * interval - board.getMu2() * Math.abs(vy) * interval);
                ball.setVelocity(new Vect(vx, vy));
            }
        }
    }

    @Override
    public void keyPressed(int key) {
        board.keyPressed(key);
    }

    @Override
    public void keyReleased(int key) {
        board.keyReleased(key);
    }

    @Override
    public void resetBoard() {
        if(savedBoard != null) {
            IGameBoard loadedBoard = savedBoard.getGameBoard();
            setBoard(loadedBoard);
        }
    }

    public void notifyChanged() {
        this.setChanged();
        this.notifyObservers();
    }

    @Override
    public void update(Observable o, Object arg) {
        this.notifyChanged();
    }

}
