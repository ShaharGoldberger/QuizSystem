package model;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;

public abstract class Question implements Serializable , Comparable<Question>, Cloneable{

	protected String theQuestion;
	private int serialNumber;
	private static int serialNum = 1;

	public Question(String theQuestion) {
		this.theQuestion = theQuestion;
		this.serialNumber = serialNum++;
	}

	public String getTheQuestion() {
		return theQuestion;
	}

	public boolean setTheQuestion(String newQuestion) {
		this.theQuestion = newQuestion;
		return true;
	}
	public void setSerialNumber(int serial) {
		this.serialNumber=serial;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public boolean equals(Question q1) {
		if (this.getTheQuestion().equals(q1.getTheQuestion())
				&& this.getClass().getSimpleName().equals(q1.getClass().getSimpleName()) && this.answersComparable()==q1.answersComparable()) {
			return true;
		}
		return false;
	}

	public String toString() {
		return serialNumber + ":" + theQuestion;
	}
	
	public void saveExamToFile(PrintWriter pw) throws FileNotFoundException {
		pw.print(serialNumber + ": ");
		pw.print(theQuestion + "\n");
	}
	
	public void saveSolutionToFile(PrintWriter pw2) {
		pw2.print(serialNumber +": ");
	}
	
	public abstract int answersComparable();
	
	public int compareTo(Question question) {
		if(this.answersComparable() < question.answersComparable())
			return -1;
		else if(this.answersComparable() > question.answersComparable())
			return 1;
		else
			return 1;
	}
	
	public int compareToWithoutDup(Question question) {
		if(this.answersComparable() < question.answersComparable())
			return -1;
		else if(this.answersComparable() > question.answersComparable())
			return 1;
		else
			return 0;
	}
	
	
	
	public Question clone() throws CloneNotSupportedException {
		return (Question) super.clone();
	}
	
	
	

}
