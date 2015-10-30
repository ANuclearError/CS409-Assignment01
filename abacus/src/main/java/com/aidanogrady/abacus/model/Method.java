package com.aidanogrady.abacus.model;

import com.github.javaparser.ast.body.Parameter;

import java.util.List;

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
     * A list of the parameters of a constructor. Due to Java syntax, this is
     * technically a constructor's unique identifer.
     */
    List<Parameter> parameters;

    /**
     * Constructor
     * @param name - name
     * @param lines - lines
     * @param parameters - parameters
     */
    public Method(String name, int lines, List<Parameter> parameters) {
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
    public List<Parameter> getParameters() {
        return parameters;
    }
}
