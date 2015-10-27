package com.aidanogrady.abacus;

import com.aidanogrady.abacus.controller.Controller;
import com.aidanogrady.abacus.view.Output;

/**
 * This is responsible for holding the main method, creating the objects that
 * kick off the control flow.
 *
 * @author Aidan O'Grady
 * @since 0.0
 */
public class Driver {

    private static final String ACRONYM = "ABACUS";

    private static final String VERSION = "v0.6";

    private static final String NAME =
            "A Benevolent Analysis of Codes' Unsavoury Smells";

    private static final String AUTHOR = "Aidan O'Grady - 201218150";

	/**
	 * The main method which sets off control flow.
	 *
	 * @param args - parameters of the program.
	 */
	public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("USAGE: ABACUS.jar <directory>");
            System.exit(0);
        }

        // Print out basic program info.
        System.out.println(ACRONYM + " " + VERSION);
        System.out.println(NAME);
        System.out.println(AUTHOR);
        Output.lineBreak();

        Controller controller = new Controller(args[0]);
        controller.start();
    }
}
