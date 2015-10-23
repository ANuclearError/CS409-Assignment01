package com.aidanogrady.abacus.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * The Menu Controller acts the controller for the menu bar of the application.
 * When a menu item is acted upon, it's corresponding method will be called.
 *
 * @author Aidan O'Grady
 * @since 0.1
 */
public class Controller {

	@FXML
	private Stage stage;

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
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Open Source Folder");

		File directory = chooser.showDialog(stage);
		if (directory != null) {
			System.out.println("Selected: " + directory.getPath());
		} else {
			System.out.println("No directory selected");
		}
	}
}
