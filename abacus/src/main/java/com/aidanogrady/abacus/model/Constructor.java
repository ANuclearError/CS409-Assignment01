package com.aidanogrady.abacus.model;

import com.github.javaparser.ast.body.Parameter;

import java.util.List;

/**
 * This class stores information about a constructor, primarily its parameters.
 *
 * @author Aidan O'Grady
 * @since 0.7
 */
public class Constructor {
    /**
     * A list of the parameters of a constructor. Due to Java syntax, this is
     * technically a constructor's unique identifer.
     */
    List<Parameter> parameters;

    /**
     * Constructor
     * @param parameters - parameters list
     */
    public Constructor(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    /**
     * Returns number of parameters
     * @return parameters.size
     */
    public int getNoOfParameters() {
        return parameters.size();
    }

    /**
     * Returns list of parameters
     * @return parameters
     */
    public List<Parameter> getParameters() {
        return parameters;
    }
}
