package model;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;

public class Answer implements Serializable {

	private String text;
	private boolean isCorrect;

	public Answer(String text, boolean isCorrect) {
		this.text = text;
		this.isCorrect = isCorrect;
	}

	public Answer(String text) {
		this.text = text;
	}

	public Answer() {
		this.text = null;
		this.isCorrect = false;
	}

	public String getText() {
		return text;
	}

	public boolean setText(String text) {
		this.text = text;
		return true;
	}

	public boolean getIsCorrect() {
		return isCorrect;
	}

	public boolean setIsCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
		return true;
	}

	public boolean equals(String newAnswerText) {
		if (newAnswerText == this.getText())
			return true;
		return false;
	}

	public String toString() {
		return text + " -" + isCorrect + "\n";
	}

	public void saveAnswerToFile(PrintWriter pw) throws FileNotFoundException {
		pw.print(text + " ");
	}

	/// memento design pattern
	public Memento createMemento() {
		return new Memento(this.text, this.isCorrect);
	}

	public void setMemento(Memento m) {
		text = m.text;
		isCorrect = m.isCorrect;
	}

	public static class Memento {
		String text;
		boolean isCorrect;

		private Memento(String text, boolean ans) {
			this.text = text;
			this.isCorrect = ans;
		}
	}

}
