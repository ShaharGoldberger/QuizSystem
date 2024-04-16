package model;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class ClosedQuestion extends Question {

	private int numOfAnswers;
	private MySetClass<Answer> allClosedAnswers;
	private final static int MAX_NUMBER_OF_ANSWERS = 10;

	public ClosedQuestion(String theQuestion) {
		super(theQuestion);
		this.allClosedAnswers = new MySetClass<Answer>(numOfAnswers);
		this.numOfAnswers = 0;
	}

	public void setNumOfAnswers(int number) {
		this.numOfAnswers = number;
	}
	public int getNumOfAnswers() {
		return numOfAnswers;
	}

	public MySetClass<Answer> getAllClosedAnswers() {
		return allClosedAnswers;
	}
	
	public MySetClass<Answer> getAllClosedAnswer(){
		return this.allClosedAnswers;
	}

	public boolean setAllClosedAnswers(MySetClass<Answer> answers) {
		this.numOfAnswers=answers.getCurrentNumberOfAnswersInTheSet();
		return allClosedAnswers.set(answers);
	}
	

	public boolean addAnswer(Answer answer) {	
		return allClosedAnswers.add(answer);
	}

	// 4
	public boolean changeAnswer(Answer ans, int index) {
		return allClosedAnswers.change(ans, index);
	}

	public void doesAnswerExist(String newAnswerText) throws Exception {
		allClosedAnswers.answerExist(newAnswerText);
	}

	// 5
	public void removeAnswer(int answerIndex) throws Exception {
		allClosedAnswers.removeAnswer(answerIndex);
	}

	// for case 7
	public boolean areAllFalse() {
		return allClosedAnswers.allFalse();
	}

	public boolean areMoreThanOneTrue() {
		return allClosedAnswers.moreThanOneTrue();
	}

	//check
	public String toString() {
		String textOfQuestion = theQuestion;
		return textOfQuestion+ "\n" +  allClosedAnswers.toString();
	}
	
	public void saveExamToFileForExam(PrintWriter pw) throws FileNotFoundException {
		saveExamToFile(pw);
		for(int i=0 ; i< numOfAnswers ; i++) {
			allClosedAnswers.get(i).saveAnswerToFile(pw);
		}
	}
	
	public void saveSolution(PrintWriter pw2) throws FileNotFoundException {
		saveSolutionToFile(pw2);
		for(int i=0 ; i< numOfAnswers ; i++) {
			allClosedAnswers.get(i).saveAnswerToFile(pw2);
			pw2.print("-->" + allClosedAnswers.get(i).getIsCorrect() + "\n");
		}
	}
	
	
	//4 methode clone
	public ClosedQuestion clone() throws CloneNotSupportedException {
		ClosedQuestion temp = (ClosedQuestion) super.clone();
		temp.allClosedAnswers = allClosedAnswers.clone();
		return temp;
	}
	
	
	public int answersComparable() {
		int sum=0;
		for(int i=0 ; i< allClosedAnswers.size()-1 ; i++) {
			if(allClosedAnswers.get(i) != null)
				sum+= allClosedAnswers.get(i).getText().length();
		}
		return sum;
	}

	
	

}
