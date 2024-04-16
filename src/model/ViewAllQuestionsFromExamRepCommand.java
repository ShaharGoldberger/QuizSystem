package model;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewAllQuestionsFromExamRepCommand implements Command {
	ExamRepository examRepository = passData.getExamRepos();

	public ViewAllQuestionsFromExamRepCommand(ExamRepository e) {
		this.examRepository = e;
	}

	@Override
	public void execute() {
		AnchorPane ap1 = new AnchorPane();
		Stage stage = new Stage();
		Scene scene = new Scene(ap1, 800, 600);
		stage.setScene(scene);
		stage.show();
		TextArea ta = new TextArea();
		ta.setPrefWidth(700);
		ta.setPrefHeight(500);
		ap1.minHeight(800);
		ap1.getChildren().clear();
		ap1.getChildren().add(ta);
		String s = examRepository.toString();
		ta.setText(s);
	}

}
