package com.aidanogrady.abacus.model;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * This visitor obtains the class name from a Class and displays it.
 *
 * @author Aidan O'Grady
 * @since 0.4
 */
public class ClassName extends VoidVisitorAdapter {
	private String name;

	@Override
	public void visit(ClassOrInterfaceDeclaration n, Object arg) {
		name = n.getName();
	}

	public String getName() {
		return name;
	}
}
