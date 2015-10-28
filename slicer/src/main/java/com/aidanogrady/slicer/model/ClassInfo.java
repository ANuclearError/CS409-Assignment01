package com.aidanogrady.slicer.model;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileInputStream;

/**
 * A sinmple class that uses JavaParser to obtain a classes' name, start and end
 * lines.
 *
 * @author Aidan O'Grady
 * @since 0.1
 */
public class ClassInfo extends VoidVisitorAdapter {
    /**
     * The name of the class.
     */
    private String className;

    /**
     * The start line of the class.
     */
    private int startLine;

    /**
     * The ending line of the class.
     */
    private int endLine;

    /**
     * Analyses the file, visiting the class and determining start and end line.
     * @param file - the file to be analysed.
     * @throws Exception - something went wrong.
     */
    public void analyse(File file) throws Exception {
        FileInputStream in = new FileInputStream(file);

        CompilationUnit cu;
        try {
            // parse the file
            cu = JavaParser.parse(in);
        } finally {
            in.close();
        }

        // visit and print the methods names
        visit(cu, null);
    }

    /**
     * Returns class name.
     * @return className
     */
    public String getClassName() {
        return className;
    }

    /**
     * Returns end line.
     * @return endLine
     */
    public int getEndLine() {
        return endLine;
    }

    /**
     * Returns start line.
     * @return startLine
     */
    public int getStartLine() {
        return startLine;
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration n, Object arg) {
        className = n.getName();
        endLine = n.getEndLine();
        startLine = n.getBeginLine();
    }
}
