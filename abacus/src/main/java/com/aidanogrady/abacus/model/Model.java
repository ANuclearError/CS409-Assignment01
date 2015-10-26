package com.aidanogrady.abacus.model;

import com.aidanogrady.abacus.model.bloaters.LargeClass;
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
     * The analyser to obtain class names.
     */
    private ClassName className;

    /**
     * The analyser to determine large classes.
     */
    private LargeClass largeClass;

    /**
     * Constructor
     */
    public Model() {
        className = new ClassName();
        largeClass = new LargeClass();
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
        className.visit(cu, null);
        largeClass.reset();
        largeClass.visit(cu, null);
    }

    /**
     * Returns the className analyser
     * @return className;
     */
    public ClassName getClassName() {
        return className;
    }

    /**
     * Returns the largeClass analyser
     * @return largeClass;
     */
    public LargeClass getLargeClass() {
        return largeClass;
    }
}
