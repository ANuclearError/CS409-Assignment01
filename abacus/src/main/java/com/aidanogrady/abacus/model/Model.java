package com.aidanogrady.abacus.model;

import com.aidanogrady.abacus.model.bloaters.ICodeSmell;
import com.aidanogrady.abacus.model.bloaters.LargeClass;
import com.aidanogrady.abacus.model.bloaters.LargeMethods;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

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
    private List<ICodeSmell> smellList;

    /**
     * Constructor
     */
    public Model() {
        className = new ClassName();
        smellList = new ArrayList<ICodeSmell>();
        smellList.add(new LargeClass());
        smellList.add(new LargeMethods());
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
        for(ICodeSmell smell : smellList) {
            smell.reset();
            smell.analyse(cu);
        }
    }

    /**
     * Returns the className analyser
     * @return className;
     */
    public ClassName getClassName() {
        return className;
    }

    /**
     * Returns list of all the code smells analysing.
     * @return list of visitors
     */
    public List<ICodeSmell> getCodeSmells() {
        return smellList;
    }
}
