package model;

import javax.swing.JOptionPane;

public class UpdateTheWordingOfAnExistingAnswerCommand implements Command{
	
	ExamRepository examRepository;
	ManagerClass managerClass;
	
	public UpdateTheWordingOfAnExistingAnswerCommand(ExamRepository e , ManagerClass m) {
		this.managerClass = m;
		this.examRepository = e;
	}

	@Override
	public void execute() {

		String s111 = JOptionPane.showInputDialog("Please choose a question index to update");
		int indexOfAnswers = 0;
		int index1 = Integer.parseInt(String.valueOf(s111.charAt(0)));
		String newWording = JOptionPane.showInputDialog(" Please enter the new text for the answer");
		if (managerClass.isTheQuestionInIndexClosedQuestion(examRepository, index1 - 1)) {
			String indexOfAnswerSt = JOptionPane.showInputDialog(" Please enter the new text for the answer");
			indexOfAnswers = Integer.parseInt(indexOfAnswerSt);
		}
		try {
			managerClass.updateWordingOfAnswer(examRepository, indexOfAnswers, newWording, index1 - 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		//JOptionPane.showMessageDialog(null, "ERRORR ! Answer not updated");
	}
	
}
