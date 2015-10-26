package com.aidanogrady.abacus.model;

import java.util.HashMap;
import java.util.Map;

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
     * Maps the name of each method to the number of lines it contains.
     */
    private Map<String, Integer> methods;

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
        methods = new HashMap<String, Integer>();
    }

    /**
     * Adds a new method to the collection of methods, along with their length.
     * @param name - the name of the method.
     * @param length - the length of the method.
     */
    public void addMethod(String name, int length) {
        methods.put(name, length);
    }

    /**
     * Returns the name of the class
     * @return class name.
     */
    public String getClassName() {
        return className;
    }

    /**
     * Returns the map of methods to their length
     * @return methods
     */
    public Map<String, Integer> getMethods() {
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
