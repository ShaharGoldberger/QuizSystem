package model;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class ExamRepository implements Serializable, Cloneable {

	private String subjectOfExam;
	private int currentNumberOfQuestionsInArray;
	private ArrayList<Question> allQuestions;

	public ExamRepository(String subjectOfExam) {
		this.subjectOfExam = subjectOfExam;
		this.currentNumberOfQuestionsInArray = 0;
		this.allQuestions = new ArrayList<Question>();
	}

	public String getSubjectOfExam() {
		return subjectOfExam;
	}

	public ArrayList<Question> getAllQuestion() {
		return allQuestions;
	}

	public boolean setAllQuestion(ArrayList<Question> questions) {
		if (questions != null) {
			for (int i = 0; i < questions.size(); i++) {
				addQuestion(questions.get(i));
			}
			return true;
		}
		return false;
	}

	public int getCurrentNumOfQuestions() {
		return currentNumberOfQuestionsInArray;
	}

	public boolean addQuestion(Question question) {
		if (question == null)
			return false;
		for (int i = 0; i < currentNumberOfQuestionsInArray; i++) {
			//if (allQuestions.get(i).getTheQuestion().equals(question.getTheQuestion())
				//	&& allQuestions.get(i).getClass().getSimpleName().equals(question.getClass().getSimpleName()))
				//return false;
			
			//if(allQuestions.get(i).equals(question))
				//return false;
		}
		
		allQuestions.add(currentNumberOfQuestionsInArray++, question);
		return true;
	}

	public boolean updateQuestion(Question newQuestion, int oldQuestionIndex) {
		if (newQuestion == null || oldQuestionIndex < 0 || oldQuestionIndex > currentNumberOfQuestionsInArray)
			return false;
		for (int i = 0; i < currentNumberOfQuestionsInArray; i++) {
			if (newQuestion.equals(allQuestions.get(i))) {
				return false;
			}
		}
		allQuestions.get(oldQuestionIndex).setTheQuestion(newQuestion.getTheQuestion());
		return true;
	}

	
	public void updateAnswer(Question question, Answer newAnswer, int index) throws Exception {
		if (newAnswer == null)
			throw new Exception("The new answer was null so we didn't use it");
		for (int i = 0; i < currentNumberOfQuestionsInArray; i++) {
			if (question.equals(allQuestions.get(i))) {
				if (allQuestions.get(i) instanceof ClosedQuestion) {
					ClosedQuestion temp = (ClosedQuestion) allQuestions.get(i);
					try {
						temp.doesAnswerExist(newAnswer.getText());
					} catch (Exception e) {
						throw e;
					}
					temp.changeAnswer(newAnswer, index);
				} else if (allQuestions.get(i) instanceof OpenQuestion) {
					OpenQuestion res = (OpenQuestion) allQuestions.get(i);
					res.changeAns(newAnswer.getText());
				}
			}
		}
	}

	public void deleteAnswer(Question question, int index) throws Exception {
		if (question == null)
			throw new Exception("The question you chose to delete an answer from, is null");
		for (int i = 0; i < currentNumberOfQuestionsInArray; i++) {
			if (question.equals(allQuestions.get(i))) {
				if (allQuestions.get(i) instanceof ClosedQuestion) {
					ClosedQuestion temp = (ClosedQuestion) allQuestions.get(i);
					try {
						temp.removeAnswer(index);
					} catch (Exception e) {
						throw e;
					}
		
				} else if (allQuestions.get(i) instanceof OpenQuestion) {
					OpenQuestion res = (OpenQuestion) allQuestions.get(i);
					res.setAnswer(null);
				}
				return;
			}

		}
		throw new Exception("The question you chose doesn't exist");
	}

	//Project part 1
	public void insertionSortInLexicographic() {
		for (int index = 1; index < currentNumberOfQuestionsInArray; index++) {
			Question key = allQuestions.get(index);
			int position = index;

			// Shift larger values to the right
			while (position > 0
					&& key.getTheQuestion().compareTo(allQuestions.get(position - 1).getTheQuestion()) < 0) {
				allQuestions.get(position).setTheQuestion(allQuestions.get(position - 1).getTheQuestion());
				position--;
			}
			allQuestions.get(position).setTheQuestion(key.getTheQuestion());
		}
	}

	public void isNewQuestion(Question newQuestion) throws Exception {
		// for example to show that the exception works.
		// newQuestion.setTheQuestion(allQuestions[2].getTheQuestion());
		for (int i = 0; i < currentNumberOfQuestionsInArray; i++) {
			if (newQuestion.equals(allQuestions.get(i))) {
				throw new Exception("This question already exists");
			}
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("The subject of the exam is : " + subjectOfExam + "\n");
		for (int i = 0; i < allQuestions.size(); i++) {
			sb.append((i + 1) + ": " + (allQuestions.get(i)).toString() + "\n");
		}
		return sb.toString();
	}
	
	public static String nameOfFile(String name) {
		String nameOfFile;
		LocalDate nowDate = LocalDate.now();
		nameOfFile = name + nowDate;
		return nameOfFile;
	}

	public void save(String fileName) throws FileNotFoundException {
		String examFileName = nameOfFile("exam_");
		String solutionsFileName = nameOfFile("solution_");
		File examFile = new File(examFileName);
		File solutionFile = new File(solutionsFileName);

		PrintWriter pw = new PrintWriter(examFile);
		PrintWriter pw2 = new PrintWriter(solutionFile);

		// to create the exam file(without solutions)
		for (int i = 0; i < currentNumberOfQuestionsInArray; i++) {
			if (allQuestions.get(i) instanceof ClosedQuestion)
				((ClosedQuestion) allQuestions.get(i)).saveExamToFileForExam(pw);
			else if (allQuestions.get(i) instanceof OpenQuestion) {
				allQuestions.get(i).saveExamToFile(pw);
			}
				
		}

		// to create the solution file
		for (int i = 0; i < currentNumberOfQuestionsInArray; i++) {
			if (allQuestions.get(i) instanceof ClosedQuestion) {
				((ClosedQuestion) allQuestions.get(i)).saveSolution(pw2);
			} else if (allQuestions.get(i) instanceof OpenQuestion)
				((OpenQuestion) allQuestions.get(i)).saveSolution(pw2);
		}
		pw.close();
		pw2.close();
	}

	public static void saveToBinaryFile(ExamRepository examRepositoryToSave) throws FileNotFoundException, IOException {
		String name = "Questions_and_Answers";
		ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream(name));
		outFile.writeObject(examRepositoryToSave);
		outFile.close();
	}

	public static ExamRepository openBinaryFile() throws FileNotFoundException, IOException, ClassNotFoundException {
		String name = "Questions_and_Answers";
		ObjectInputStream inFile = new ObjectInputStream(new FileInputStream(name));
		ExamRepository examRepositoryToOpen = (ExamRepository) inFile.readObject();
		inFile.close();
		return examRepositoryToOpen;
	}
	
	public boolean doesFileExists(String fileName) {
		File f= new File(fileName);
		return f.isFile();
	}
	
	public void setNewSerialNumber() {
		for(int i=0 ; i< this.currentNumberOfQuestionsInArray ; i++) {
			allQuestions.get(i).setSerialNumber(i+1);
		}
	}

	// 3 in project part 2
	public void bubbleSortByAnswers() {
		for (int i = currentNumberOfQuestionsInArray - 1; i > 0; i--) {
			for (int j = 0; j < i; j++) {
				if (allQuestions.get(j).compareTo(allQuestions.get(j + 1)) > 0) {
					Collections.swap(allQuestions, j, j + 1);
				}
			}
		}
	}
	public void setCurrentNumberOfQuestionsInArray(int currentNumOfQuestions) {
		currentNumberOfQuestionsInArray = currentNumOfQuestions;
	}

	// for case 8 --> create copy of exam by method Clone
	public static ExamRepository clone(ExamRepository examToClone) throws CloneNotSupportedException {
		return (ExamRepository) examToClone.clone();
	}

	@Override
	public ExamRepository clone () {
		try {
			ExamRepository clone = (ExamRepository) super.clone();
			// TODO: copy mutable state here, so the clone can't change the internals of the original
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}
	
}
