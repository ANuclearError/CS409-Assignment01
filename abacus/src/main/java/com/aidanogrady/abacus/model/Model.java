package com.aidanogrady.abacus.model;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.File;
import java.io.FileInputStream;

/**
 * The Model class runs all the analysis on a file given to it. It will
 * sequentially go through each code smell and analyze its given class.
 *
 * @author Aidan O'Grady
 * @since 0.3
 */
public class Model {

    /**
     * The analyser to obtain information on classes.
     */
    private Analyser analyser;

    /**
     * Constructor
     */
    public Model() {
        analyser = new Analyser();
    }

    /**
     * Analyses the current file for all code smells.
     *
     * @throws Exception - something has gone wrong.
     */
    public void analyse(File file) throws Exception {
        // creates an input stream for the file to be parsed
        FileInputStream in = new FileInputStream(file);

        CompilationUnit cu;
        try {
            // parse the file
            cu = JavaParser.parse(in);
        } finally {
            in.close();
        }

        // visit and print the methods names
        analyser.reset();
        analyser.visit(cu, null);
    }

    public Results getResults() {
        return analyser.getResults();
    }
}
