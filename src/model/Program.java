package model;

import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DrbgParameters.NextBytes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class Program {

	public static MySetClass<Answer> getAllAnswersForClosedQuestion(String[] answers, boolean[] bolValue)
			throws Exception {

		MySetClass<Answer> allAnswersForClosedQuestion = new MySetClass<Answer>(answers.length - 1);

		for (int i = 0; i < answers.length; i++) {
			allAnswersForClosedQuestion.add(new Answer(answers[i], bolValue[i]));
		}
		return allAnswersForClosedQuestion;
	}

	public static boolean chooseQuestion(String s) {
		char ch;
		ch = s.charAt(0);
		if (ch == 'c' || ch == 'C' || ch == 'o' || ch == 'O') {
			return true;
		}
		return false;
	}

	public static int getIndexOfQuestion1(String s) {
		char ch = s.charAt(0);
		int indexOfQuestion = Integer.parseInt(String.valueOf(ch));
		if (indexOfQuestion > 0) {
			return indexOfQuestion;
		}
		return 0;
	}

	// Reserved for Case 6 Manager Class
	public static int getNumOfAnswers(Scanner s) {
		int numOfAnswers;
		System.out.println("Please enter the number of answer");
		numOfAnswers = s.nextInt();
		try {
			if (numOfAnswers > 10 || numOfAnswers < 0)
				throw new Exception("Wanted to add too many answers. We will let you put 4 answers");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			numOfAnswers = 4;
		}
		return numOfAnswers;
	}
	

	public static String[] menu() {
		String[] op = new String[17];
		op[0] = "1 - View all the questions in the repository";
		op[1] = "2 - Adding a question and answer to the repository";
		op[2] = "3 - Update the wording of an existing questions";
		op[3] = "4 - Update the wording of an existing answer";
		op[4] = "5 - Delete an answer from a question";
		op[5] = "6 - Create a exam manually";
		op[6] = "7 - Create a exam automatically";
		op[7] = "8 - create copy of exam";
		op[8] = "9 - Collection (TreeSet)";
		op[9] = "10 - Collection (HashSet) ";
		op[10] = "11 - Convert from TreeSet to MyArrayList + print MyArrayList";
		op[11] = "12 - Print With Iterator Design pattern and Print with java iterator";
		op[12] = "13 - Remove the last element";
		op[13] = "14 - Print with Button";
		op[14] = "15 - Remove with Button";
		op[15] = "16 - Memento Design Pattern";
		op[16] = "17 - Exit\n";
		return op;
	}

	public static ExamRepository hardCodedQuestions(ExamRepository exam) {
		ArrayList<Question> allQuestions = new ArrayList<Question>();
		MySetClass<Answer> allClosedAnswersForQ3 = new MySetClass<Answer>();
		MySetClass<Answer> allClosedAnswersForQ4 = new MySetClass<Answer>();
		MySetClass<Answer> allClosedAnswersForQ5 = new MySetClass<Answer>();

		Answer ans0 = new Answer("white", true);
		Answer ans1 = new Answer("In the sea", true);
		Answer ans2 = new Answer("ant", false);
		
		allQuestions.add(new OpenQuestion("What is the color of a polar bear? ", ans0));
		allQuestions.add(new OpenQuestion("Where do fish live? ", ans1));
		allQuestions.add(new OpenQuestion("Which animal lives on trees? ", ans2));
		
		allQuestions.add(3, new ClosedQuestion("Where do cheetas live? "));
		allClosedAnswersForQ3.add(new Answer("Uganda", true));
		allClosedAnswersForQ3.add(new Answer("Israel", false));
		allClosedAnswersForQ3.add(new Answer("Spain", false));
		allClosedAnswersForQ3.add(new Answer("Morocco", false));
		allClosedAnswersForQ3.add(new Answer("England", false));
		allClosedAnswersForQ3.add(new Answer("China", true));
		allClosedAnswersForQ3.add(new Answer("Japan", true));
		allClosedAnswersForQ3.add(new Answer("Canada", true));

		((ClosedQuestion) allQuestions.get(3)).setAllClosedAnswers(allClosedAnswersForQ3);

		allQuestions.add(4, new ClosedQuestion("Which of the following animals eats bananas? "));

		allClosedAnswersForQ4.add(new Answer("Dog", false));
		allClosedAnswersForQ4.add(new Answer("Dolphin", false));
		allClosedAnswersForQ4.add(new Answer("Monkey", false));
		allClosedAnswersForQ4.add(new Answer("Chicken", false));
		allClosedAnswersForQ4.add(new Answer("Cat", false));
		allClosedAnswersForQ4.add(new Answer("Horse", false));
		allClosedAnswersForQ4.add(new Answer("Tiger", false));
		allClosedAnswersForQ4.add(new Answer("Elafant", true));

		((ClosedQuestion) allQuestions.get(4)).setAllClosedAnswers(allClosedAnswersForQ4);
		
		// i add to 
		allQuestions.add(5, new ClosedQuestion("Which of the following animals eats bananas? "));

		allClosedAnswersForQ5.add(new Answer("Dog", false));
		allClosedAnswersForQ5.add(new Answer("Dolphin", false));
		allClosedAnswersForQ5.add(new Answer("Monkey", false));
		allClosedAnswersForQ5.add(new Answer("Chicken", false));
		allClosedAnswersForQ5.add(new Answer("Cat", false));
		allClosedAnswersForQ5.add(new Answer("Horse", false));
		allClosedAnswersForQ5.add(new Answer("Tiger", false));
		allClosedAnswersForQ5.add(new Answer("Elafant", true));

		((ClosedQuestion) allQuestions.get(5)).setAllClosedAnswers(allClosedAnswersForQ5);

		exam.setAllQuestion(allQuestions);
		return exam;
	}
	
	

//In order to support the creation of exams in different topics, we need to create in the main program other examRepositorys for other subjects and than we can send them to the manager class.
//than in the main we need decide which repository to use in each case. 

}
