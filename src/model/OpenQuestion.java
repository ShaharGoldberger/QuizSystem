package model;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class OpenQuestion extends Question{

	private Answer answer;

	public OpenQuestion(String theQuestion, Answer answer) {
		super(theQuestion);
		this.answer = answer;
		this.answer.setIsCorrect(true);
	}

	public OpenQuestion(String theQuestion) {
		super(theQuestion);
	}

	public Answer getAnswer() {
		return answer;
	}

	public boolean setAnswer(Answer answer) {
		this.answer = answer;
		return true;
	}

	public void changeAns(String str) {
		this.answer.setText(str);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(theQuestion);
		if (answer != null) {
			sb.append("\n\tThe answer is: " + this.answer);
		}
		return sb.toString();
	}
	
	public void saveExamToFileForExam(PrintWriter pw) throws FileNotFoundException {
		saveExamToFile(pw);
	}
	
	public void saveSolution(PrintWriter pw2) throws FileNotFoundException {
		saveSolutionToFile(pw2);
		// save the answer
		answer.saveAnswerToFile(pw2);
		pw2.print("\n");
	}
	
	public int answersComparable() {
		return this.answer.getText().length();
	}
	
	public OpenQuestion clone() throws CloneNotSupportedException {
		return (OpenQuestion) super.clone();
	}

}
