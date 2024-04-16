package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class ManagerClass<T> {

	// function for case 2
	public void addQuestionAndAnswer(ExamRepository examRepository, String questionText,
			MySetClass<Answer> allAnswersForClosedQuestion, char ch, int numOfAnswers, String answer) throws Exception {
		Scanner sh = new Scanner(System.in);
		if (ch == 'C' || ch == 'c') {
			ClosedQuestion c1 = new ClosedQuestion(questionText);
			try {
				examRepository.isNewQuestion(c1);
			} catch (Exception e) {
				throw e;
			}

			c1.setAllClosedAnswers(allAnswersForClosedQuestion);
			examRepository.addQuestion(c1);
		} else if (ch == 'O' || ch == 'o') {
			OpenQuestion c2 = new OpenQuestion(questionText);
			try {
				examRepository.isNewQuestion(c2);
			} catch (Exception e) {
				throw e;
			}
			c2.setAnswer(new Answer(answer, true));
			examRepository.addQuestion(c2);
		}
	}

	// function for case 3
	public void updateWordingForQuestion(ExamRepository examRepository, int indexOfQuestion, String newQuestionText)
			throws Exception {
		if (examRepository.getAllQuestion().get(indexOfQuestion - 1).getClass() == ClosedQuestion.class) {
			ClosedQuestion question = new ClosedQuestion(newQuestionText);
			try {
				examRepository.isNewQuestion(question);
			} catch (Exception e) {
				throw e;
			}
			examRepository.updateQuestion(question, indexOfQuestion - 1);

		} else {
			OpenQuestion question = new OpenQuestion(newQuestionText);
			try {
				examRepository.isNewQuestion(question);
			} catch (Exception e) {
				throw e;
			}
			examRepository.updateQuestion(question, indexOfQuestion - 1);
		}
	}

	// functions for case 4
	public boolean isTheQuestionInIndexClosedQuestion(ExamRepository examRepository, int index) {
		if (examRepository.getAllQuestion().get(index).getClass() == ClosedQuestion.class) {
			return true;
		} else
			return false;
	}

	// 4
	public void updateWordingOfAnswer(ExamRepository examRepository, int indexOfAnswers, String newWording,
			int indexOfQuestion) throws Exception {
		if (examRepository.getAllQuestion().get(indexOfQuestion).getClass() == ClosedQuestion.class) {
			try {
				examRepository.updateAnswer((examRepository.getAllQuestion().get(indexOfQuestion)),
						new Answer(newWording, false), indexOfAnswers - 1);
			} catch (Exception e) {
				throw e;
			}
		} else {
			try {
				examRepository.updateAnswer((examRepository.getAllQuestion().get(indexOfQuestion)),
						new Answer(newWording), 0);
			} catch (Exception e) {
				throw e;
			}
		}
	}

	// function for case 5
	public void deleteAnswerFromQuestion(ExamRepository examRepository, int indexOfQuestion, int indexOfAnswer)
			throws Exception {
		if (examRepository.getAllQuestion().get(indexOfQuestion - 1).getClass() == ClosedQuestion.class) {
			try {
				examRepository.deleteAnswer(examRepository.getAllQuestion().get(indexOfQuestion - 1),
						indexOfAnswer - 1);
			} catch (Exception e) {
				throw e;
			}
		} else { // open question
			try {
				examRepository.deleteAnswer(examRepository.getAllQuestion().get(indexOfQuestion - 1), 0);
			} catch (Exception e) {
				throw e;
			}
		}
	}

	// functions for case 6
	public ExamRepository createExamManually(ExamRepository examRepository, int[] numOfQuestions) {
		ExamRepository examByUser = new ExamRepository("Animals");
		int i = 0;
		ArrayList<Question> temp = examRepository.getAllQuestion();

		int[] indexForQuestions = choosenQuestionsNumber(numOfQuestions);

		while (i < numOfQuestions.length) {
			if (temp.get(indexForQuestions[i] - 1) instanceof ClosedQuestion) {
				int j = 0;
				int[] indexForAnswers = choosenAnswersNumber(4);

				ClosedQuestion closedQuestionTemp = new ClosedQuestion(
						temp.get(indexForQuestions[i] - 1).getTheQuestion());

				MySetClass<Answer> answersTemp = ((ClosedQuestion) temp.get(indexForAnswers[i] - 1))
						.getAllClosedAnswers();
				while (j < 4) {
					closedQuestionTemp.addAnswer(answersTemp.get(indexForAnswers[j] - 1));
					j++;
				}
				closedQuestionTemp.addAnswer(new Answer("All answers are false", false));
				closedQuestionTemp.addAnswer(new Answer("There is more than one correct answer", false));
				examByUser.addQuestion(closedQuestionTemp);
			} else if (temp.get(indexForQuestions[i] - 1) instanceof OpenQuestion) {
				examByUser.addQuestion(temp.get(indexForQuestions[i] - 1));
			}
			i++;
		}
		examByUser.bubbleSortByAnswers();
		return examByUser;
	}

	// for case 6
	public int[] choosenQuestionsNumber(int[] numOfQuestions) {
		Scanner s = new Scanner(System.in);
		int[] indexForQuestions = new int[numOfQuestions.length];
		int choosen;
		for (int i = 0; i < numOfQuestions.length; i++) {
			choosen = numOfQuestions[i];
			indexForQuestions[i] = choosen;
			for (int j = 0; j < i; j++) {
				if (choosen == indexForQuestions[j]) {
					System.out.println("Please enter a different number for a question ");
					i--;
					break;
				}
			}
		}
		return indexForQuestions;
	}

	// for case 6
	public int[] choosenAnswersNumber(int numOfAnswers) {
		Scanner s = new Scanner(System.in);
		int[] indexForAnswers = new int[numOfAnswers];
		int chosen;
		for (int i = 0; i < numOfAnswers; i++) {
			chosen = Program.getNumOfAnswers(s);
			indexForAnswers[i] = chosen;
			for (int j = 0; j < i; j++) {
				if (chosen == indexForAnswers[j]) {
					System.out.println("Please enter a different number for an answer ");
					i--;
					break;
				}
			}
		}
		return indexForAnswers;
	}

	// functions for case 7
	// for case 7
	public ExamRepository createExamEutomatic(ExamRepository examRepository, int numOfQuestions) {
		Scanner s = new Scanner(System.in);
		ExamRepository automaticExam = new ExamRepository("Animals");
		int i = 0;

		// random a different index for questions and answers
		int[] indexForQuestions = checkRandomNumber(numOfQuestions);
		int[] indexForAnswers = checkRandomNumber(4);

		ArrayList<Question> temp = examRepository.getAllQuestion();

		while (i < numOfQuestions) {
			if (temp.get(indexForQuestions[i] - 1) instanceof ClosedQuestion) {
				ClosedQuestion closedQuestionTemp = new ClosedQuestion(
						temp.get(indexForQuestions[i] - 1).getTheQuestion());

				MySetClass<Answer> answersTemp = ((ClosedQuestion) temp.get(indexForQuestions[i] - 1))
						.getAllClosedAnswers();

				int j = 0;
				while (j < 4) {
					boolean flag = true;
					if (answersTemp.get(indexForAnswers[j] - 1).getIsCorrect() == false)
						closedQuestionTemp.addAnswer(answersTemp.get(indexForAnswers[j] - 1));
					else if (answersTemp.get(indexForAnswers[j] - 1).getIsCorrect() == true && flag == true) {
						closedQuestionTemp.addAnswer(answersTemp.get(indexForAnswers[j] - 1));
						flag = false;
					}
					j++;
				}
				closedQuestionTemp.addAnswer(new Answer("All answers are false", closedQuestionTemp.areAllFalse()));
				closedQuestionTemp.addAnswer(
						new Answer("There is more than one correct answer", closedQuestionTemp.areMoreThanOneTrue()));
				closedQuestionTemp.setNumOfAnswers(6);
				automaticExam.addQuestion(closedQuestionTemp);
			} else if (temp.get(indexForQuestions[i] - 1) instanceof OpenQuestion) {
				automaticExam.addQuestion(temp.get(indexForQuestions[i] - 1));
			}
			i++;
		}
		// Sort the questions in lexicographic order
		// automaticExam.insertionSortInLexicographic();
		automaticExam.setCurrentNumberOfQuestionsInArray(numOfQuestions);
		automaticExam.bubbleSortByAnswers();
		automaticExam.setNewSerialNumber();
		return automaticExam;
	}

	// for case 7
	public int[] checkRandomNumber(int numOfQuestionsByUser) {
		int[] bingo = new int[numOfQuestionsByUser];
		int randomN;
		boolean flag = false;
		for (int i = 0; i < numOfQuestionsByUser; i++) {
			flag = false;
			randomN = (int) (Math.random() * numOfQuestionsByUser) + 1;
			for (int j = 0; j < i; j++) {
				if (randomN == bingo[j])
					flag = true;
			}
			if (flag) {
				i--;
				continue;
			}
			bingo[i] = randomN;
		}
		return bingo;
	}

	public Set<?> convertFromArrayListToCollectionTreeSet(ArrayList<Question> arrayListToConvert) {
		Set<Question> ts = new TreeSet<Question>();
		boolean flag;
		for (int i = 0; i < arrayListToConvert.size(); i++) {
			flag = ts.add(arrayListToConvert.get(i));
		}
		return ts;
	}

	public String printSetWithIterator(Set<?> s) {
		Iterator<?> it = s.iterator();
		String res = " ";
		while (it.hasNext()) {
			Question q = (Question) it.next();
			res = res + q.toString() + "\n";
		}
		return res;
	}

	public HashSet<?> convertFromTreeSetToHashSet(TreeSet<Question> ts) {
		HashSet<Question> newHashSet = new HashSet<Question>();
		Iterator<Question> itForTree = ts.iterator();
		Iterator<Question> itForHash;
		boolean flag = false;

		while (itForTree.hasNext()) {
			Question q = itForTree.next();
			System.out.println(q.theQuestion + " ");

			itForHash = newHashSet.iterator();
			while (itForHash.hasNext()) {
				if (q.equals(itForHash.next())) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				newHashSet.add(q);
			}
			flag = false;
		}
		return newHashSet;
	}

	public MyArrayList<?> toConveyFromCollectionToMyArrayList(Collection<?> collection) {
		MyArrayList<?> newMyArrayList = new MyArrayList<T>();
		Iterator<?> it = collection.iterator();
		while (it.hasNext()) {
			Question q = (Question) it.next();
			newMyArrayList.add(q);
		}
		return newMyArrayList;
	}

	public ArrayList<?> toConveyFromMyArrayListToJavaArrayList(MyArrayList<?> myArrLst) {
		ArrayList<Question> newArrList = new ArrayList<>();
		Iterator<?> it = myArrLst.iterator();
		while (it.hasNext()) {
			Question q = (Question) it.next();
			newArrList.add(q);
		}
		return newArrList;
	}

	// print with myArrList iter
	public String printMyArrListWithIteratorDesignPattern(MyArrayList<Question> arrListToPrint) {
		Iterator<?> it = arrListToPrint.iterator();
		String res = " ";
		while (it.hasNext()) {
			Question q = (Question) it.next();
			res = res + q.toString() + "\n";
		}
		
		return res;
	}

	// print with java iter
	public String printMyArrListWithJavaIterator(ArrayList<Question> arrListToPrint) {
		Iterator<?> it = arrListToPrint.iterator();
		String res = " ";
		while (it.hasNext()) {
			Question q = (Question) it.next();
			res = res + q.toString() + "\n";
		}
		return res;
	}

	public void removeTheLastEleWithMyIterator(MyArrayList<Question> arrListToRemoveTheLast) {
		Iterator<T> it = (Iterator<T>) arrListToRemoveTheLast.iterator();
		while (it.hasNext())
			it.next();
		it.remove();
	}

	public void removeTheLastEleWithJavaIterator(ArrayList<Question> arrLst) {
		Iterator<Question> it = arrLst.iterator();
		while (it.hasNext())
			it.next();
		it.remove();
	}

	public ArrayList<Question> convertFromMyArrListToJavaArrList(MyArrayList<Question> myArrList) {
		Iterator<Question> it = myArrList.iterator();
		ArrayList<Question> arrList = new ArrayList<>();
		while (it.hasNext()) {
			Question q = (Question) it.next();
			arrList.add(q);
		}
		return arrList;
	}

	public void clickOnButton(MyButton b, Iterator<?> it, TextArea ta) {
		b.setOnAction(event -> {
			String res = "\n\n\n";
			while (it.hasNext()) {
				Question q = (Question) it.next();
				res = res + q.toString() + "\n";
			}
			ta.setText(res);
		});
	}
	
	public void clickOnButtonToRemove(MyButton b, Iterator<?> it, TextArea ta) {
		b.setOnAction(event -> {
			String res = "\n\n\n";
			while (it.hasNext()) {
				Question q = (Question) it.next();
				if(it.hasNext()) {
					res = res + q.toString() + "\n";
				} else
					it.remove();
			}
			ta.setText(res);
		});
	}

}