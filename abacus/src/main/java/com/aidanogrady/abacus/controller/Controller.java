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
     * @param results - the results to be displayed
     */
    private void showResults(Results results) {
        Rating rating;
        String name = results.getClassName();
        int fields = results.getNoOfFields();
        int methods = results.getNoOfMethods();

        Output.print("Class: " + name);
        Output.lineBreak();

        // Data classes only contain getters and setters, does not actually
        // do any operations
        if (results.getIsDataClass()) {
            Output.print("Class only has getters and setter methods");
            Output.print("I'd maybe look into this.\n");
        }

        rating = Ratings.getClassRating(fields, methods);
        if (!rating.equals(Rating.GOOD)) {
            Output.print(rating + " WARNING!");
            Output.print("\t" + fields + " fields");
            Output.print("\t" + methods + " methods");
            Output.lineBreak();
        }

        if(!results.getFields().isEmpty()) {
            showFieldResults(results.getFields());
            Output.lineBreak();
        }

        if(!results.getConstructors().isEmpty()) {
            showConstructorResults(results.getConstructors());
            Output.lineBreak();
        }

        showMethodResults(results.getMethods());
        Output.lineBreak();
    }

    private void showFieldResults(List<Field> fields) {
        int prims = 0;
        Output.print("Fields");
        Output.minorLineBreak();
        for (Field f : fields) {
            if(f.isPrimitive()) {
                prims++;
                Output.print(f.getName() + " is primitive.");
                Output.print("\t Might wanna look into this.");
            }
        }

        if (prims > (fields.size() / 2)) {
            Output.print("Over half are primitive, maybe obsessed?");
        }
    }

    /**
     * Shows the results for each constructor.
     * @param constructors - the constructors analysed.
     */
    private void showConstructorResults(List<Constructor> constructors) {
        Rating rating;
        int params;
        Output.print("Constructors");
        Output.minorLineBreak();
        for (Constructor c : constructors) {
            params = c.getNoOfParameters();
            if (params > 0) {
                Output.print("Parameters: " + c.getParameters().toString());
                rating = Ratings.getParametersRating(params);
                if (!rating.equals(Rating.GOOD)) {
                    Output.print(rating + " WARNING: " + params + " params");
                }
            } else {
                Output.print("No parameters");
            }
        }
    }

    /**
     * Shows the results for each method.
     * @param methods - the methods analysed.
     */
    private void showMethodResults(List<Method> methods) {
        Rating pRating;
        Rating mRating;
        String name;
        int lines;
        int param;
        Output.print("Methods");
        Output.minorLineBreak();
        for (Method method : methods) {
            name = method.getName();
            lines = method.getLines();
            param = method.getParameters();
            mRating = Ratings.getMethodLinesRating(lines);
            pRating = Ratings.getParametersRating(param);
            if (!mRating.equals(Rating.GOOD) || !pRating.equals(Rating.GOOD)) {
                Output.print(name);
                Output.print("\t" + mRating + " WARNING: " + lines + " lines");
                Output.print("\t" + pRating + " WARNING: " + param + " params");
            }
        }
    }
}
