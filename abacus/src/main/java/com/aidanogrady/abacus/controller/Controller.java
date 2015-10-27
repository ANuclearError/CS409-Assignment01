package com.aidanogrady.abacus.controller;

import com.aidanogrady.abacus.model.*;
import com.aidanogrady.abacus.view.Input;
import com.aidanogrady.abacus.view.Output;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The controller class is the main hub of the system. It controls the flow of
 * the program, taking the user input and acting upon it.
 *
 * @author Aidan O'Grady
 * @since 0.3
 */
public class Controller {
    /**
     * The directory of the project to be analysed.
     */
    private File directory;

    /**
     * The list of files in the directory.
     */
    private List<File> files;

    /**
     * The model that will analyse the code.
     */
    private Model model;

    /**
     * Constructor
     *
     * @param path - the path of the source code's directory.
     */
    public Controller(String path) {
        this.directory = new File(path);
        final String[] SUFFIX = {"java"};
        Collection<File> col = FileUtils.listFiles(directory, SUFFIX, true);
        files = new ArrayList<File>(col);
        model = new Model();
    }

    /**
     * The start of the program.
     */
    public void start() {
        Output.print("You have chosen the directory:");
        Output.print(directory);
        Output.minorLineBreak();
        try {
            loop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The main loop of the program, allowing the user to view different files
     * until they wish to quit the program.
     */
    private void loop() throws Exception {
        while (true) {
            File chosen = chooseFile();
            model.analyse(chosen);
            Output.lineBreak();
            showResults(model.getResults());
        }
    }

    /**
     * Prompts the user to choose a file to be analysed.
     *
     * @return file to be analysed.
     */
    private File chooseFile() {
        Output.print("Which file would you like to analyse?");
        Output.print("Enter 0 if you would like to quit.");
        Output.printTrimmedList(files, directory.getAbsolutePath());

        int response = 0;
        try {
            response = Input.getInteger();
            return files.get(response - 1);
        } catch(IndexOutOfBoundsException e) {
            if (response == 0) {
                Output.print("Goodbye.");
                System.exit(0);
            }
            Output.integerException(response + "");
            return chooseFile();
        }
    }

    /**
     * Displays the result of analysis to the user.
     */
    private void showResults(Results results) {
        Rating rating;
        String name = results.getClassName();
        int fields = results.getNoOfFields();
        int methods = results.getNoOfMethods();

        Output.print("Class: " + name);
        Output.minorLineBreak();

        Output.print("Number of fields: " + fields);
        Output.print("Number of methods: " + methods);
        rating = Ratings.getClassRating(fields, methods);
        Output.print("Large Classing Rating: " + rating);

        Output.minorLineBreak();
        showMethodResults(results.getMethods());
        Output.lineBreak();
    }

    /**
     * Shows the results for each method.
     * @param methods - the methods analysed.
     */
    private void showMethodResults(List<Method> methods) {
        Rating rating;
        String name;
        int lines;
        int params;
        for (Method method : methods) {
            name = method.getName();
            lines = method.getLines();
            params = method.getParameters();
            Output.print("Method: " + name);
            rating = Ratings.getMethodLinesRating(lines);
            Output.print("\tNumber of lines: " + lines + " " + rating);
            rating = Ratings.getParametersRating(params);
            Output.print("\tNumber of parameters: " + params + " " + rating);
        }
    }
}
