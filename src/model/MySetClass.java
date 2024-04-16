package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import model.Answer.Memento;

public class MySetClass<T> implements Serializable {

	private final int MAX_ANSWERS_IN_SET = 10;
	// T because the array contains only closed answers
	private T[] allAnswersInSet;
	private int currentNumberOfAnswersInTheSet;
	//for design pattern
	private T[] mementoArr;

	public MySetClass(int currentNumberOfAnswersInTheSet) {
		this.allAnswersInSet = (T[]) new Object[currentNumberOfAnswersInTheSet + 1];
		this.currentNumberOfAnswersInTheSet = currentNumberOfAnswersInTheSet;
	}

	public MySetClass() {
		this.allAnswersInSet = (T[]) new Object[MAX_ANSWERS_IN_SET];
		this.currentNumberOfAnswersInTheSet = 0;
	}

	public T[] getAllAnswersInSet() {
		return allAnswersInSet;
	}

	public int getCurrentNumberOfAnswersInTheSet() {
		return currentNumberOfAnswersInTheSet;
	}

	public void setCurrentNumberOfAnswersInTheSet(int currentNumberOfAnswersInTheSet) {
		this.currentNumberOfAnswersInTheSet = currentNumberOfAnswersInTheSet;
	}

	public int getMAX_ANSWERS_IN_SET() {
		return MAX_ANSWERS_IN_SET;
	}

	public void removeAnswer(int answerIndex) throws Exception {
		if (allAnswersInSet[answerIndex] != null) {
			swapAnswersIndex(allAnswersInSet, answerIndex, currentNumberOfAnswersInTheSet - 1);
			allAnswersInSet[currentNumberOfAnswersInTheSet--] = null;
		} else
			throw new Exception("The answer in the index chosen doesn't exist");
	}

	private void swapAnswersIndex(T[] answers, int indexOne, int indexTwo) {
		T temp = answers[indexOne];
		answers[indexOne] = answers[indexTwo];
		answers[indexTwo] = temp;
	}

	@SuppressWarnings("unchecked")
	public boolean add(Answer answer) {
		if (answer == null)
			return false;
		for (int i = 0; i < currentNumberOfAnswersInTheSet; i++) {
			if (allAnswersInSet[i] == answer)
				return false;
		}
		int size;
		if (currentNumberOfAnswersInTheSet == 0)
			size = 1;
		else {
			size = allAnswersInSet.length;
			allAnswersInSet = Arrays.copyOf(allAnswersInSet, size * 2);
		}
		allAnswersInSet[currentNumberOfAnswersInTheSet++] = (T) answer;
		return true;
	}

	public boolean set(MySetClass<Answer> answers) {
		if (answers.currentNumberOfAnswersInTheSet <= MAX_ANSWERS_IN_SET) {
			for (int i = 0; i < answers.currentNumberOfAnswersInTheSet; i++) {
				add(answers.get(i));
			}
			return true;
		}
		return false;
	}

	public int size() {
		return allAnswersInSet.length;
	}

	public MySetClass<T> clone() throws CloneNotSupportedException {
		MySetClass<T> temp = (MySetClass<T>) super.clone();
		temp.allAnswersInSet = (T[]) allAnswersInSet.clone();
		return temp;
	}

	public T get(int indexToGet) {
		return allAnswersInSet[indexToGet];
	}

	public boolean change(Answer ans, int index) {
		if (ans == null)
			return false;
		((Answer) allAnswersInSet[index]).setText(ans.getText());

		return true;
	}

	public void answerExist(String newAnswerText) throws Exception {
		for (int i = 0; i < this.currentNumberOfAnswersInTheSet; i++) {
			if (this.allAnswersInSet[i].equals(newAnswerText)) {
				throw new Exception("This example_project.Answer already exists!");
			}
		}
	}

	public boolean allFalse() {
		for (int i = 0; i < this.currentNumberOfAnswersInTheSet; i++) {
			if (((Answer) allAnswersInSet[i]).getIsCorrect()) {
				return false;
			}
		}
		return true;
	}

	public boolean moreThanOneTrue() {
		boolean foundOneTrue = false;
		for (int i = 0; i < this.currentNumberOfAnswersInTheSet; i++) {
			if (((Answer) allAnswersInSet[i]).getIsCorrect() && foundOneTrue)
				return true;
			else if (((Answer) allAnswersInSet[i]).getIsCorrect())
				foundOneTrue = true;
		}
		return false;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < this.currentNumberOfAnswersInTheSet; i++) {
			sb.append("\t" + (i + 1) + " )" + ((Answer) allAnswersInSet[i]).toString());
		}
		return sb.toString();
	}
	
	
	public void createMementoArr() {
		this.mementoArr = (T[]) new Object[this.currentNumberOfAnswersInTheSet];
		for(int i=0 ; i<this.currentNumberOfAnswersInTheSet ; i++) {
			mementoArr[i] = (T) ((Answer) allAnswersInSet[i]).createMemento();
		}
	}
	
	
	public void setMementoArr() {
		this.allAnswersInSet = (T[]) new Object[this.currentNumberOfAnswersInTheSet];
		for(int i=0 ; i<mementoArr.length ; i++) {
			allAnswersInSet[i] = (T) new Answer();
			((Answer) allAnswersInSet[i]).setMemento((Memento) mementoArr[i]);
		}

	}

	
	
	

}
