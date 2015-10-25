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
 * @since 1.0
 */
public class Model {

    /**
     * The file currently being analysed.
     */
    private File file;

    /**
     * Returns the currently analysed file.
     * @return file
     */
    public File getFile() {
        return file;
    }

    /**
     * Sets a new file to be analysed.
     * @param file - the new file to be analysed.
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Analyses the current file for all code smells.
     *
     * @throws Exception - something has gone wrong.
     */
    public void analyse() throws Exception {
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
        LargeClass largeClass = new LargeClass();
        largeClass.visit(cu, null);
    }
}
