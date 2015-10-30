package com.aidanogrady.abacus.model;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * This class does all the analysis on the code. Whenever it encounters data it
 * is interested in (for example, field declarations) it will then record these
 * instances in accordance with the smell.
 *
 * @author Aidan O'Grady
 * @since 0.6
 */
public class Analyser extends VoidVisitorAdapter {

    /**
     * The results of this analysis.
     */
    private Results results;

    /**
     * Constructor
     */
    public Analyser() {
        results = new Results();
    }

    /**
     * Returns results of this analysis.
     * @return results
     */
    protected Results getResults() {
        return results;
    }

    /**
     * Resets analysis for new class.
     */
    protected void reset() {
        results = new Results();
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration n, Object arg) {
        results.setClassName(n.getName());
        super.visit(n, arg); // Ensures below methods are called
    }

    @Override
    public void visit(FieldDeclaration n, Object arg) {
        results.addField(n.getVariables().toString(), n.getType());
    }

    @Override
    public void visit(ConstructorDeclaration n, Object arg) {
        results.addConstructor(n.getParameters());
    }

    @Override
    public void visit(MethodDeclaration n, Object arg) {
        int startLine = n.getBeginLine();
        int endLine = n.getEndLine();
        int length = endLine - startLine;
        String name = n.getName();
        results.addMethod(name, length, n.getParameters());

        // Since we have a method that isn't a getter/setter, we probably don't
        // have a data class. Could maybe be more sophisticated.
        if(!name.startsWith("get") && !name.startsWith("set")) {
            results.setIsDataClass(false);
        }
    }
}
