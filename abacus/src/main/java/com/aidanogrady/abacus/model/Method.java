package com.aidanogrady.abacus.model;

/**
 * This class holds collected information about a method.
 *
 * @author Aidan O'Grady
 * @since 0.6
 */
public class Method {
    /**
     * The name of the method.
     */
    private String name;

    /**
     * The number of lines the method is.
     */
    private int lines;

    /**
     * The number of parameters the method has.
     */
    private int parameters;

    /**
     * Constructor
     * @param name - name
     * @param lines - lines
     * @param parameters - parameters
     */
    public Method(String name, int lines, int parameters) {
        this.name = name;
        this.lines = lines;
        this.parameters = parameters;
    }

    /**
     * Returns name of method.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns number of lines of method.
     * @return lines
     */
    public int getLines() {
        return lines;
    }

    /**
     * Returns number of parameters of method.
     * @return parameters.
     */
    public int getParameters() {
        return parameters;
    }
}
