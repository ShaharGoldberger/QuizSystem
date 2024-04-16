package model;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UpdateTheWordingOfAnExistingQuestionsCommand implements Command{
	ExamRepository examRepository;
	ManagerClass managerClass;
	
	public UpdateTheWordingOfAnExistingQuestionsCommand(ExamRepository e , ManagerClass m) {
		this.managerClass = m;
		this.examRepository = e;
	}

	@Override
	public void execute() {
		String s11 = JOptionPane.showInputDialog("Please choose a question index to update");
		int index = Program.getIndexOfQuestion1(s11);
		if (index != 0) {
			String newWordingText = JOptionPane.showInputDialog("Please enter the new text for the question");
			try {
				managerClass.updateWordingForQuestion(examRepository, index, newWordingText);
				JOptionPane.showMessageDialog(null, "Question updated sucessfully ");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "ERRORR ! Question not Updated ");
			}
		} else {
			JOptionPane.showMessageDialog(null, "ERRORR ! Index cannot be less than 1");
		}
	}

}
