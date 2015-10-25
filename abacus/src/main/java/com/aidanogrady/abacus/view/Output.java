package com.aidanogrady.abacus.view;

import java.util.List;

/**
 * The Output class is responsible for passing information from the model to the
 * user. Any information that is to be presented to the user is to be passed
 * through to this, to avoid the case of having System.out.println statements
 * placed throughout the whole program and to allow for quick ways to print
 * commonly needed strings.
 *
 * @author Aidan O'Grady
 * @since 0.2
 */
public class Output {

    /**
     * The prompt is displayed before any user input.
     */
    private static final String PROMPT = "> ";

    /**
     * How many characters a line break string is to take up.
     */
    private static final int LINE_BREAK_INT = 80;

    /**
     * Character for major line breaks.
     */
    private static final char LINE_BREAK = '=';

    /**
     * Character for minor line breaks.
     */
    private static final char SMALL_LINE_BREAK = '-';

    /**
     * Prints a blank line to the user output.
     */
    public static void line(){
        System.out.println();
    }

    /**
     * Prints an object to the user output.
     * @param line - the object to be displayed.
     */
    public static void print(Object line){
        System.out.println(line);
    }

    /**
     * Prints out a prompt for when the user is to enter text.
     */
    public static void prompt(){
        System.out.print(PROMPT);
    }

    /**
     * Prints a string of a single continuous line break in order to split up
     * the output to look more clean.
     */
    public static void lineBreak() {
        for (int i = 0; i < LINE_BREAK_INT; i++){
            System.out.print(LINE_BREAK);
        }
        System.out.println();
    }

    /**
     * Prints a string of a single continuous line break in order to split up
     * the output to look more clean.
     */
    public static void lineBreak(int num) {
        for (int i = 0; i < num; i++){
            System.out.print(LINE_BREAK);
        }
        System.out.println();
    }

    /**
     * Prints a string of a single continuous line break in order to split up
     * the output to look more clean. The minor line break displays a character
     * that is not as impactful as a regular line break.
     */
    public static void minorLineBreak() {
        for (int i = 0; i < LINE_BREAK_INT; i++){
            System.out.print(SMALL_LINE_BREAK);
        }
        System.out.println();
    }

    /**
     * Prints a string of a single continuous line break in order to split up
     * the output to look more clean. The minor line break displays a character
     * that is not as impactful as a regular line break.
     */
    public static void minorLineBreak(int num) {
        for (int i = 0; i < num; i++){
            System.out.print(SMALL_LINE_BREAK);
        }
        System.out.println();
    }

    /**
     * Prints an error message when the user inputs a string when an integer
     * was expected, displaying the original input that caused the error.
     * @param cause - the invalid string that the user input
     */
    public static void integerException(String cause){
        System.out.println("Invalid input, please enter a number.\n\t "
                + "Your input: '" + cause + "'");
    }

    /**
     * Prints an error message when the user inputs an invalid string when 'yes'
     * or 'no' was expected, displaying the original input that caused the
     * error.
     * @param cause - the invalid string that the iuser input
     */
    public static void boolException(String cause){
        System.out.println("Invalid input, please enter 'yes' or 'no\n\t "
                + "Your input: '" + cause + "'");
    }

    public static <T> void printList(List<T> list) {
        for(int i = 0; i < list.size(); i++){
            T item = list.get(i);
            Output.print("\t" + (i+1) + ". " + item);
        }
    }

    public static <T> void printTrimmedList(List<T> list, String line) {
        for(int i = 0; i < list.size(); i++){
            T item = list.get(i);
            String string = item.toString().replace(line, "");
            Output.print("\t" + (i+1) + ". " + string);
        }
    }
}