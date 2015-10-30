public class DataClumps {
    private int x;
    private int y;

    private int width;
    private int height;

    public DataClumps(int x, int y, int width, int height) {
        move(x, y);
        scale(width, height);
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void scale(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
