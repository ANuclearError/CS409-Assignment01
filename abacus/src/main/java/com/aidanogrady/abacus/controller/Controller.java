package com.aidanogrady.abacus.controller;

import com.aidanogrady.abacus.model.*;
import com.aidanogrady.abacus.view.Input;
import com.aidanogrady.abacus.view.Output;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.Type;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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
        String name = results.getClassName();
        int fields = results.getNoOfFields();
        int methods = results.getNoOfMethods();

        Output.print("Class: " + name);

        // Data classes only contain getters and setters, does not actually
        // do any operations
        if (results.getIsDataClass()) {
            Output.print("Class only has getters and setter methods");
            Output.print("I'd maybe look into this.\n");
        }

        Rating fRating = Ratings.getClassFieldsRating(fields);
        Rating mRating = Ratings.getClassMethodsRating(methods);
        if (!fRating.equals(Rating.GOOD) || !mRating.equals(Rating.GOOD)) {
            Output.print("\t" + fRating + " WARNING: " + fields + " fields");
            Output.print("\t" + mRating + " WARNING: " + fields + " methods");
        }
        Output.lineBreak();

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

        List<Set<Parameter>> dataClumps = results.getDataClumps();
        if (!dataClumps.isEmpty()) {
            Output.print("The following possible data clumps were detected");
            for (Set<Parameter> dataClump : dataClumps) {
                Output.print("\t" + dataClump);
            }
            Output.lineBreak();
        }
    }

    private void showFieldResults(List<Field> fields) {
        Output.print("Fields");
        Output.minorLineBreak();
        boolean warning = false;

        if (!fields.isEmpty()) {
            for (Field f : fields) {
                int prims = 0;
                if (f.isPrimitive()) {
                    prims++;
                    String name = f.getName();
                    Type type = f.getType();
                    Output.print("Primitive" + name + "(" + type + ")");
                    Output.print("\t Might wanna look into this.");
                    warning = true;
                }
                if (prims > (fields.size() / 2)) {
                    Output.print("Over half are primitive, maybe obsessed?");
                }
            }
        }

        // No smells detected
        if (!warning) {
            Output.print("No problems here.");
        }

    }

    /**
     * Shows the results for each constructor.
     * @param constructors - the constructors analysed.
     */
    private void showConstructorResults(List<Constructor> constructors) {
        Output.print("Constructors");
        Output.minorLineBreak();

        boolean warning = false;
        if (!constructors.isEmpty()) {
            for (Constructor c : constructors) {
                int params = c.getNoOfParameters();
                if (params > 0) {
                    Rating rating = Ratings.getParametersRating(params);
                    if (!rating.equals(Rating.GOOD)) {
                        Output.print("Params: " + c.getParameters().toString());
                        Output.print(rating + " warning for param length");
                        warning = true;
                    }
                } else {
                    Output.print("No parameters");
                }
            }
        }

        // No smells detected
        if (!warning) {
            Output.print("No problems here.");
        }
    }

    /**
     * Shows the results for each method.
     * @param methods - the methods analysed.
     */
    private void showMethodResults(List<Method> methods) {
        boolean warning = false;
        Output.print("Methods");
        Output.minorLineBreak();
        for (Method method : methods) {
            String name = method.getName();
            int lines = method.getLines();
            int param = method.getParameters().size();
            Rating mRating = Ratings.getMethodLinesRating(lines);
            Rating pRating = Ratings.getParametersRating(param);
            if (!mRating.equals(Rating.GOOD) || !pRating.equals(Rating.GOOD)) {
                Output.print(name);
                Output.print("\t" + mRating + " WARNING: " + lines + " lines");
                Output.print("\t" + pRating + " WARNING: " + param + " params");
                warning = true;
            }
        }

        // No smells detected
        if (!warning) {
            Output.print("No problems here.");
        }
    }
}
