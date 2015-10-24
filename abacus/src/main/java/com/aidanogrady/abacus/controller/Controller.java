package com.aidanogrady.abacus.controller;

import com.aidanogrady.abacus.view.About;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

import java.io.File;

/**
 * The Menu Controller acts the controller for the menu bar of the application.
 * When a menu item is acted upon, it's corresponding method will be called.
 *
 * @author Aidan O'Grady
 * @since 0.1
 */
public class Controller {

	private About about;

    @FXML
    private Text folderPath;

    @FXML
    private Text display;

    public Controller() {
        about = new About();
    }

	@FXML
	public void about() {
		about.showAbout();
	}

	@FXML
	public void displayDataClumps() {
		display.setText("Data Clumps");
	}

	@FXML
	public void displayLargeClasses() {
        display.setText("Large Classes");
	}

	@FXML
	public void displayLargeMethods() {
        display.setText("Large Methods");
	}

	@FXML
	public void displayLongParamLists() {
        display.setText("Long Parameter List");
	}

	@FXML
	public void displayPrimitiveObsession() {
        display.setText("Primitive Obsession");
	}

	@FXML
	public void exit() {
		System.exit(0);
	}

	@FXML
	public void open() {
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Open Source Folder");

		File directory = chooser.showDialog(null);
		if (directory != null) {
            folderPath.setText(directory.getPath());
		}
	}
}
