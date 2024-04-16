package javafxExamSystem;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Command;
import model.ExamRepository;
import model.ManagerClass;
import model.Program;
import model.Question;
import model.ViewAllQuestionsFromExamRepCommand;
import model.passData;

import java.io.IOException;

import controller.*;

public class ViewMain {

	Stage mainStagel;
	ManagerClass managerClass = new ManagerClass();
	ExamRepository examRepository = new ExamRepository("Animals");
	passData pd = new passData();

	public ViewMain(Stage mainS) {
		ExamRepository examR = Program.hardCodedQuestions(examRepository);
		pd.setExamrepos(examR);

		Text appname = new Text("Welcome to Exams System");
		appname.setFont(Font.font("arial", 24));
		//RadioButton rd[] = new RadioButton[16];
		RadioButton rd[] = new RadioButton[17];

		ToggleGroup tg = new ToggleGroup();
		String[] radioLabels = Program.menu();

		mainS.setTitle("Exam System");
		VBox vbox = new VBox(10);
		vbox.getChildren().add(appname);
		vbox.setMargin(appname, new Insets(10, 10, 10, 200));
		vbox.setPrefWidth(800);

		for (int i = 0; i < rd.length; i++) {
			rd[i] = new RadioButton(radioLabels[i]);
			rd[i].setToggleGroup(tg);
			vbox.getChildren().add(rd[i]);
			VBox.setMargin(rd[i], new Insets(5, 5, 5, 5));
			rd[i].setToggleGroup(tg);
		}

		System.out.println(rd[1].isSelected());
		AnchorPane ap = new AnchorPane(vbox);
		Scene scene = new Scene(ap, 805, 805);

		tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n) {

				RadioButton rb = (RadioButton) tg.getSelectedToggle();
				//String s = rb.getText();
				//char option = s.charAt(0);
				//int inOption = Integer.parseInt(String.valueOf(option));
				
				String s = rb.getText();
				String option;
				if(s.charAt(1) == ' ') {
					option = s.substring(0, 1);
				}else {
					option = s.substring(0, 2);
				}
				
				int inOption = Integer.parseInt(String.valueOf(option)); //Integer.parseInt(s);

				if (rb != null) {
					try {
						ExamController qz = new ExamController(inOption, managerClass);
					} catch (ClassNotFoundException | IOException | CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		});

		mainStagel = mainS;
		mainStagel.setScene(scene);
		mainStagel.show();

	}

}
