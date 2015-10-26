package com.aidanogrady.abacus.model.bloaters;

import com.aidanogrady.abacus.model.Rating;
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
 * @since 1.0
 */
public class LargeClass extends VoidVisitorAdapter {

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

    /**
     * Returns the number of fields in this class.
     * @return number of fields
     */
    public int getNoOfFields() {
        return noOfFields;
    }

    /**
     * Returns the number of methods in this class.
     * @return number of methods.
     */
    public int getNoOfMethods() {
        return noOfMethods;
    }

    /**
     * Returns the rating of this code smell.
     * @return rating
     */
    public Rating getRating() {
        if (noOfFields > MAX_FIELDS && noOfMethods > MAX_METHODS)
            return Rating.BAD;
        if (noOfFields > MAX_FIELDS || noOfMethods > MAX_METHODS)
            return Rating.OKAY;
        return Rating.GOOD;
    }

    /**
     * Resets the counts for when a new class is visited.
     */
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