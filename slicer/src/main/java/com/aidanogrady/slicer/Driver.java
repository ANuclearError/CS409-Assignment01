package com.aidanogrady.slicer;

import com.aidanogrady.slicer.controller.Controller;
import com.aidanogrady.slicer.view.Output;

/**
 * This is responsible for holding the main method, creating the objects that
 * kick off the control flow.
 *
 * @author Aidan O'Grady
 * @since 0.0
 */
public class Driver {

    private static final String ACRONYM = "SLICER";

    private static final String VERSION = "v0.1";

    private static final String NAME =
            "Slicing Lines of Interesting Code Extremely Roughly";

    private static final String AUTHOR = "Aidan O'Grady - 201218150";

    /**
     * The main method which sets off control flow.
     *
     * @param args - parameters of the program.
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            Output.print("USAGE: SLICER.jar <directory>");
            System.exit(0);
        }

        // Print out basic program info.
        Output.print(ACRONYM + " " + VERSION);
        Output.print(NAME);
        Output.print(AUTHOR);
        Output.lineBreak();

        Controller controller = new Controller(args[0]);
        controller.start();
    }
}
