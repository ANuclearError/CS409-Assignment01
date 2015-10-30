package com.aidanogrady.abacus.model;

/**
 * This class gives rating based on the given object being analysed. It gives a
 * rating based on whatever smell is being analysed, with each smell having its
 * own method.
 *
 * @author Aidan O'Grady
 * @since 0.7
 */
public class Ratings {

    public static Rating getClassFieldsRating(int fields) {
        int fieldMin = 10;
        int fieldMax = 20;
        if (fields < fieldMin)
            return Rating.GOOD;
        if (fields > fieldMax)
            return Rating.BAD;
        return Rating.DODGY;
    }

    public static Rating getClassMethodsRating(int methods) {
        int methodMin = 20;
        int methodMax = 30;
        if (methods < methodMin)
            return Rating.GOOD;
        if (methods < methodMax)
            return Rating.BAD;
        return Rating.DODGY;
    }

    public static Rating getMethodLinesRating(int lines) {
        int min = 30;
        int max = 60;
        if (lines < min)
            return Rating.GOOD;
        if (lines > max)
            return Rating.BAD;
        return Rating.DODGY;
    }

    public static Rating getParametersRating(int params) {
        int min = 3;
        int max = 5;
        if (params < min)
            return Rating.GOOD;
        if (params > max)
            return Rating.BAD;
        return Rating.DODGY;
    }
}
