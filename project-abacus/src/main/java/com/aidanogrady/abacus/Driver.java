package com.aidanogrady.abacus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is responsible for holding the main method, creating the objects that
 * kick off the control flow.
 *
 * @author Aidan O'Grady
 * @since 0.0
 */
public class Driver extends Application{

	/**
	 * The main method which sets off control flow.
	 *
	 * @param args - parameters of the program.
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		String fxml = "/com/aidanogrady/project_abacus/fxml/abacus.fxml";
		Parent root = FXMLLoader.load(getClass().getResource(fxml));
		Scene scene = new Scene(root, 1280, 720);

		primaryStage.setTitle("ABACUS");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
