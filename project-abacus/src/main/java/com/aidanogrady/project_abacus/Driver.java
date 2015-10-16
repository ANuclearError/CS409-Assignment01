package com.aidanogrady.project_abacus;

/**
 * This is responsible for holding the main method, creating the objects that
 * kick off the control flow.
 *
 * @author Aidan O'Grady
 * @since 0.0
 */
public class Driver {

	/**
	 * The main method which sets off control flow.
	 *
	 * @param args - parameters of the program.
	 */
	public static void main(String[] args) {
		// Right now we're not actually doing anything, so print info!
		String acronym = "Project ABACUS";
		String version = "v0.0";
		String name = "A Benevolent Analysis of Codes' Unsavoury Smells";
		String author = "By Aidan O'Grady";
		System.out.println(acronym + " " + version);
		System.out.println(name);
		System.out.println(author);
		for (int i = 0; i < 80; i++) {
			System.out.print("-");
		}
		System.out.println();
	}
}
