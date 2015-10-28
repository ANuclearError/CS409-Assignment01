package com.aidanogrady.abacus.model;

import com.github.javaparser.ast.type.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for sotring fields, each have a name and type.
 *
 * @author Aidan O'Grady
 * @since 0.8
 */
public class Field {
    String name;

    Type type;

    public Field(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public boolean isPrimitive() {
        List<String> primTypes = new ArrayList<String>();
        String[] prims = {"byte", "short", "int", "long", "float",
        "double", "boolean", "char", "String"};
        primTypes.addAll(Arrays.asList(prims));
        return primTypes.contains(type.toString());
    }
}
