package com.aidanogrady.abacus.model.bloaters;

import com.github.javaparser.ast.CompilationUnit;

/**
 * An interface for smells. All smells have a name, rating and need to be reset
 * whenever they are given a new class to analyse.
 *
 * @author Aidan O'Grady
 * @since 0.5
 */
public interface ICodeSmell {
	/**
	 * Begins the analysis of the code.
	 */
	void analyse(CompilationUnit cu);

	/**
	 * Returns name of smell.
	 * @return name
	 */
	String getName();

	/**
	 * Returns string of detailed results of analysis.
	 * @return
	 */
	String getDetails();

	/**
	 * Resets the counts for when a new class is visited.
	 */
	void reset();
}
