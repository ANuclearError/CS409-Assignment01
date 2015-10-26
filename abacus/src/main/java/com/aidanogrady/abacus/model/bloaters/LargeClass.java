package com.aidanogrady.abacus.model.bloaters;

import com.aidanogrady.abacus.model.Rating;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * This class is responsible for analysing instances of large classes in a Java
 * project.
 *
 * A large class is defined as a class that has too many fields or methods. It
 * is to be suggested that when a class has too many fields or methods, it
 * should be removed by attempting to encapsulate as many of these fields and/or
 * methods as possible.
 *
 * @author Aidan O'Grady
 * @since 0.3
 */
public class LargeClass extends VoidVisitorAdapter implements ICodeSmell{

    /**
     * The maximum number of fields allowed before a bad rating is given.
     */
    private static final int MAX_FIELDS = 20;

    /**
     * The maximum number of methods allowed before a bad rating is given.
     */
    private static final int MAX_METHODS = 20;

    /**
     * The number of fields in a Java class.
     */
    private int noOfFields;

    /**
     * The number of methods in a Java class.
     */
    private int noOfMethods;

    /**
     * Constructor.
     */
    public LargeClass() {
        noOfFields = 0;
        noOfMethods = 0;
    }

    public void analyse(CompilationUnit cu) {
        visit(cu, null);
    }

    public String getName() {
        return "Large Classes";
    }

    public Rating getRating() {
        if (noOfFields > MAX_FIELDS && noOfMethods > MAX_METHODS)
            return Rating.BAD;
        if (noOfFields > MAX_FIELDS || noOfMethods > MAX_METHODS)
            return Rating.OKAY;
        return Rating.GOOD;
    }

    public String getRatingDetails() {
        String detail = "";
        detail += "No. of fields: " + noOfFields + " / " + MAX_FIELDS + "\n";
        detail += "No. of methods: " + noOfMethods + " / " + MAX_METHODS;
        return detail;
    }

    public void reset() {
        noOfFields = 0;
        noOfMethods = 0;
    }

    @Override
    public void visit(FieldDeclaration n, Object arg) {
        noOfFields++;
    }

    @Override
    public void visit(MethodDeclaration n, Object arg) {
        noOfMethods++;
    }
}
