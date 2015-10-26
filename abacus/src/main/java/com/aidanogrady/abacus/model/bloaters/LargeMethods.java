package com.aidanogrady.abacus.model.bloaters;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.*;

/**
 * This class is responsible for analysing instances of large methods in a
 * class.
 */
public class LargeMethods extends VoidVisitorAdapter implements ICodeSmell {

	/**
	 * Maps the name of each method to the number of lines it contains.
	 */
	private Map<String, Integer> methods;

	public void analyse(CompilationUnit cu) {
		visit(cu, null);
	}

	public String getName() {
		return "Large Methods";
	}


	public String getDetails() {
		String details = "";

		for(Map.Entry<String, Integer> entry : methods.entrySet()) {
			details += entry.getKey() + ": " + entry.getValue() + " lines.\n";
		}
		return details;
	}

	public void reset() {
		methods = new HashMap<String, Integer>();
	}

	@Override
	public void visit(MethodDeclaration n, Object arg) {
		int startLine = n.getBeginLine();
		int endLine = n.getEndLine();
		int length = endLine - startLine;
		methods.put(n.getName(), length);
	}
}



