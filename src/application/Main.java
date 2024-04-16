package application;


import javafx.application.Application;
import javafx.stage.Stage;
import javafxExamSystem.ViewMain;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;



public class Main extends Application {
	
	private static Stage primaryStage;



	public static Stage getStage() {
		return primaryStage;
	}
	
	@Override
	public void start(Stage primaryStage) {
		ViewMain vm = new ViewMain(primaryStage);
		
	}
	
	public static void main(String[] args) {
		launch(args);
		
	}
}
