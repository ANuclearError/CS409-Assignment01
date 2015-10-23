package com.aidanogrady.abacus.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Created by Aidan O'Grady on 22/10/15.
 *
 */
public class MenuController {

	@FXML
	public void about(ActionEvent actionEvent) {
		System.out.println("Showing about");
	}

	@FXML
	public void displayDataClumps(ActionEvent actionEvent) {
		System.out.println("Displaying data clumps screen.");
	}

	@FXML
	public void displayLargeClasses(ActionEvent actionEvent) {
		System.out.println("Displaying large classes screen.");
	}

	@FXML
	public void displayLargeMethods(ActionEvent actionEvent) {
		System.out.println("Displaying large methods screen.");
	}

	@FXML
	public void displayLongParamLists(ActionEvent actionEvent) {
		System.out.println("Displaying long parameter list screen.");
	}

	@FXML
	public void displayPrimitiveObsession(ActionEvent actionEvent) {
		System.out.println("Displaying primitive obsession screen.");
	}

	@FXML
	public void exit(ActionEvent actionEvent) {
		System.out.println("Exiting");
		System.exit(0);
	}

	@FXML
	public void open(ActionEvent actionEvent) {
		System.out.println("Opening folder");
	}
}
