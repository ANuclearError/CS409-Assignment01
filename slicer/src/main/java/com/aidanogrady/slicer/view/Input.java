package com.aidanogrady.slicer.view;

import java.util.Scanner;

/**
 * The Input class receives the input from the user and parses it to be sent to
 * controller to call the model. The Input class will handle retrieving values
 * of different types, such as strings, booleans and integers, and will deal
 * with any problems arising from this parsing so that they need not be dealt
 * with elsewhere, making those parts simpler.
 *
 * @author Aidan O'Grady
 * @since 0.0
 */
public class Input {
    /*
     * The following string constants are for the getYesNo method, being the
     * four acceptable inputs to this method. The whole words or just 'n' and
     * 'y' are deemed to be acceptable answers.
     */
    private static final String YES = "yes";

    private static final String NO = "no";

    private static final String Y = "y";

    private static final String N = "n";

    /*
     * The scanner that is used to retrieve input from the user.
     */
    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Returns the string the user inputs into the scanner.
     *
     * @return the string scanned from the input
     */
    public static final String getString() {
        Output.prompt();
        String input = SCANNER.nextLine();
        return input;
    }

    /**
     * Returns the integer the user inputs into the scanner. If the input is not
     * an integer, then an error message is displayed and this is recursively
     * called.
     *
     * @return the int scanned from the input
     */
    public static final int getInteger() {
        Output.prompt();

        if(SCANNER.hasNextInt()){
            int next = SCANNER.nextInt();
            SCANNER.nextLine();
            return next;
        } else{
            Output.integerException(SCANNER.next());
            return getInteger();
        }
    }

    /**
     * Returns a boolean based on the answer of a Yes/No question. If the input
     * does not map to yes or no, then an error message is displayed and this is
     * recursively called.
     * @return
     */
    public static final boolean getYesNo(){
        Output.prompt();
        String input = SCANNER.nextLine().toLowerCase();

        if (input.equals(YES) || input.equals(Y)) {
            return true;
        }

        if (input.equals(NO) || input.equals(N)) {
            return false;
        }
        Output.boolException(input);
        return getYesNo();
    }
}