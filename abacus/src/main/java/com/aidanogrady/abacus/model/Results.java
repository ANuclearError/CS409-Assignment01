package com.aidanogrady.abacus.model;

import com.github.javaparser.ast.body.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * The results class contains all the results of the smell. It contains each of
 * the criteria of the smells so that they cna then be displayed to user in the
 * future.
 *
 * @author Aidan O'Grady
 * @since 0.6
 */
public class Results {
    /**
     * The name of the class being analysed.
     */
    private String className;

    /**
     * List of all constructors in a class.
     */
    private List<Constructor> constructors;

    /**
     * Maps the name of each method to the number of lines it contains.
     */
    private List<Method> methods;

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
    public Results () {
        noOfFields = 0;
        noOfMethods = 0;
        constructors = new ArrayList<Constructor>();
        methods = new ArrayList<Method>();
    }

    public void addConstructor(List<Parameter> parameters) {
        constructors.add(new Constructor(parameters));
    }

    /**
     * Adds a new method to the collection of methods, along with their length.
     * @param name - the name of the method.
     * @param length - the length of the method.
     * @param params - the number of parameters method has.
     */
    public void addMethod(String name, int length, int params) {
        methods.add(new Method(name, length, params));
    }

    /**
     * Returns the name of the class
     * @return class name.
     */
    public String getClassName() {
        return className;
    }

    /**
     * Returns list of constructors
     * @return constructors
     */
    public List<Constructor> getConstructors() {
        return constructors;
    }
    /**
     * Returns the list of methods
     * @return methods
     */
    public List<Method> getMethods() {
        return methods;
    }

    /**
     * Returns number of fields in a class.
     * @return number of fields.
     */
    public int getNoOfFields() {
        return noOfFields;
    }

    /**
     * Returns the number of methods in a class.
     * @return number of methods.
     */
    public int getNoOfMethods() {
        return noOfMethods;
    }

    /**
     * Increments the number of fields in class by one.
     */
    public void incrementNoOfFields() {
        noOfFields++;
    }

    /**
     * Increments the number of methods in class by one.
     */
    public void incrementNoOfMethods() {
        noOfMethods++;
    }

    /**
     * Sets the name of the class being analysed.
     * @param className - name of class.
     */
    public void setClassName(String className) {
        this.className = className;
    }
}
