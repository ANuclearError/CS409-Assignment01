package com.aidanogrady.abacus.model;

import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.Type;

import java.util.*;

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
     * List of all fields in a class.
     */
    private List<Field> fields;

    /**
     * List of all constructors in a class.
     */
    private List<Constructor> constructors;

    private boolean isDataClass;

    /**
     * Maps the name of each method to the number of lines it contains.
     */
    private List<Method> methods;

    /**
     * Constructor.
     */
    public Results () {
        fields = new ArrayList<Field>();
        constructors = new ArrayList<Constructor>();
        methods = new ArrayList<Method>();
        isDataClass = true;
    }

    /**
     * Adds a new field to the collection of fields.
     * @param name - the name of the field.
     * @param type - the type of the field
     */
    public void addField(String name, Type type) {
        fields.add(new Field(name, type));
    }


    /**
     * Adds new constructor to collection of them.
     * @param parameters - the parameters of this new method.
     */
    public void addConstructor(List<Parameter> parameters) {
        constructors.add(new Constructor(parameters));
    }

    /**
     * Adds a new method to the collection of methods, along with their length.
     * @param name - the name of the method.
     * @param length - the length of the method.
     * @param params - the number of parameters method has.
     */
    public void addMethod(String name, int length, List<Parameter> params) {
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
     * Returns whether class is data class or not.
     * @return is data class
     */
    public boolean getIsDataClass() {
        return isDataClass;
    }

    /**
     * Returns classes' list of fields.
     * @return
     */
    public List<Field> getFields() {
        return fields;
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
        return fields.size();
    }

    /**
     * Returns the number of methods in a class.
     * @return number of methods.
     */
    public int getNoOfMethods() {
        return methods.size();
    }

    /**
     * Sets the name of the class being analysed.
     * @param className - name of class.
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Sets whether or not class is a data class.
     * @param isDataClass - is the class a data class?
     */
    public void setIsDataClass(boolean isDataClass) {
        this.isDataClass = isDataClass;
    }

    public List<Set<Parameter>> getDataClumps() {
        List<Set<Parameter>> dataClumps = new ArrayList<Set<Parameter>>();

        // Get parameters
        Set<Parameter> params = new HashSet<Parameter>();
        for (Constructor c : constructors) {
            params.addAll(c.getParameters());
        }
        for (Method m : methods) {
            params.addAll(m.getParameters());
        }

        // get powerSet, removing sets of size 0 and 1
        Iterator<Set<Parameter>> powerSet = powerSet(params).iterator();
        while (powerSet.hasNext()) {
            Set<Parameter> set = powerSet.next();

            if (set.size() > 1 && (countOccurrences(set) > 1)) {
                dataClumps.add(set);
            }
        }

        // now to remove sets that aren't data clumps.
        return dataClumps;
    }

    private int countOccurrences(Set<Parameter> dataClump) {
        int occurrences = 0;

        for (Constructor c : constructors) {
            if (c.getParameters().containsAll(dataClump))
                occurrences++;
        }
        for (Method m : methods) {
            if (m.getParameters().containsAll(dataClump))
                occurrences++;
        }

        return occurrences;

    }

    /*
     * Borrowed from:
     * http://stackoverflow.com/questions/1670862/obtaining-a-powerset-of-a-set-in-java
     *
     * Tweaked because I don't want sets of size one.
     */
    private static <T> Set<Set<T>> powerSet(Set<T> originalSet) {
        Set<Set<T>> sets = new HashSet<Set<T>>();
        if (originalSet.isEmpty()) {
            sets.add(new HashSet<T>());
            return sets;
        }
        List<T> list = new ArrayList<T>(originalSet);
        T head = list.get(0);
        Set<T> rest = new HashSet<T>(list.subList(1, list.size()));
        for (Set<T> set : powerSet(rest)) {
            Set<T> newSet = new HashSet<T>();
            newSet.add(head);
            newSet.addAll(set);
            sets.add(newSet);
            sets.add(set);
        }
        return sets;
    }
}
