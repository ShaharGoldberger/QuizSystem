package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;

import javax.management.loading.PrivateClassLoader;
import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import model.Answer;
import model.Command;
import model.UpdateTheWordingOfAnExistingQuestionsCommand;
import model.ExamRepository;
import model.ManagerClass;
import model.MyArrayList;
import model.MyButton;
import model.MyLevel;
import model.MySetClass;
import model.Observer;
import model.OpenQuestion;
import model.Program;
import model.Question;
import model.UpdateTheWordingOfAnExistingAnswerCommand;
import model.ViewAllQuestionsFromExamRepCommand;
import model.passData;

public class ExamController {
	ManagerClass managerClass = new ManagerClass();
	ExamRepository examRepository = new ExamRepository("Animals");
	ExamRepository examByUser = null;
	ExamRepository automaticExam = null;

	public ExamController(int option, ManagerClass mc)
			throws FileNotFoundException, ClassNotFoundException, IOException, CloneNotSupportedException {
		managerClass = mc;
		examRepository = passData.getExamRepos();

		// For the new project
		Set<Question> ts = new TreeSet<Question>();
		ts = (Set<Question>) managerClass.convertFromArrayListToCollectionTreeSet(examRepository.getAllQuestion());
		TreeSet<Question> newTs = (TreeSet<Question>) ((TreeSet<Question>) ts).descendingSet(); // sort
		TreeSet<Question> save1 = (TreeSet<Question>) ((TreeSet<Question>) ts).descendingSet();
		TreeSet<Question> save2 = (TreeSet<Question>) ((TreeSet<Question>) ts).descendingSet();
		TreeSet<Question> save3 = (TreeSet<Question>) ((TreeSet<Question>) ts).descendingSet();

		Set<Question> hashSetToTreeSet1 = new HashSet<>();
		hashSetToTreeSet1 = (Set<Question>) managerClass.convertFromTreeSetToHashSet(newTs);

		// for remove function in myarraylist class
		MyArrayList<Question> arrListWithDesPat1 = new MyArrayList<Question>();
		arrListWithDesPat1 = managerClass.toConveyFromCollectionToMyArrayList(hashSetToTreeSet1);
		MyArrayList<Question> arrListWithDesPat2 = new MyArrayList<Question>();
		arrListWithDesPat2 = managerClass.toConveyFromCollectionToMyArrayList(hashSetToTreeSet1);
		// to show printing with java iterator and my iterator
		MyArrayList<Question> arrListWithDesPat3 = new MyArrayList<Question>();
		arrListWithDesPat3 = managerClass.toConveyFromCollectionToMyArrayList(save3);

		//default value
		Command c = null;

		switch (option) {
		case 1:
			// AnchorPane ap1 = new AnchorPane();
			// Stage stage = new Stage();
			// Scene scene = new Scene(ap1, 800, 600);
			// stage.setScene(scene);
			// stage.show();
			// TextArea ta = new TextArea();
			// ta.setPrefWidth(700);
			// ta.setPrefHeight(500);
			// ap1.minHeight(800);
			// ap1.getChildren().clear();
			// ap1.getChildren().add(ta);
			// String s = examRepository.toString();
			// ta.setText(s);

			c = new ViewAllQuestionsFromExamRepCommand(examRepository);
			// c.execute(examRepository);
			break;

		case 2:
			String s1 = JOptionPane.showInputDialog("(Type C/c for closed question) OR (O/o for open question)");
			char ch = s1.charAt(0);
			if (Program.chooseQuestion(s1)) {
				System.out.println(s1);
				if (ch == 'o' || ch == 'O') {
					String questionText = JOptionPane.showInputDialog("Please enter your question");
					String answerText = JOptionPane.showInputDialog("Please enter your answer");
					try {
						managerClass.addQuestionAndAnswer(examRepository, questionText, null, ch, 0, answerText);
						JOptionPane.showMessageDialog(null, "Question added sucessfully ");
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "ERRORR ! Question not added ");

					}
				} else if (ch == 'c' || ch == 'C') {
					String questionText = JOptionPane.showInputDialog("Please enter your question");
					String answerOptions = JOptionPane.showInputDialog("Enter Number of Options for the Question");
					int answerofOptionsInt = Integer.parseInt(answerOptions);
					if (answerofOptionsInt > 10 || answerofOptionsInt < 0) {
						JOptionPane.showMessageDialog(null, "Can't add more 4 Answers");
					} else {
						String answers[] = new String[answerofOptionsInt];
						boolean[] bolVal = new boolean[answerofOptionsInt];

						for (int i = 0; i < answers.length; i++) {
							Dialog<Pair<String, String>> dialog = new Dialog<>(); // Creating Dialog for 2 inputs
							dialog.setTitle("Login Dialog");

							ButtonType loginButtonType = new ButtonType("Enter", ButtonData.OK_DONE);
							dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL); // Creating
																												// action
																												// button
																												// for
																												// dialog
																												// box

							GridPane grid = new GridPane(); // creating grid to set in dialog box
							grid.setHgap(10);
							grid.setVgap(10);
							grid.setPadding(new Insets(20, 150, 10, 10));

							TextField answer = new TextField(); // Creating TexftField for dialog box
							answer.setPromptText("Answer");
							TextField bolValue = new TextField();// Creating TexftField for dialog box
							bolValue.setPromptText("true/false");

							grid.add(new Label("Answer"), 0, 0); // adding label before Textfield
							grid.add(answer, 1, 0);
							grid.add(new Label("T/F"), 0, 1);// adding label before Textfield
							grid.add(bolValue, 1, 1);

							dialog.getDialogPane().setContent(grid);

							dialog.setResultConverter(dialogButton -> {
								if (dialogButton == loginButtonType) {
									return new Pair<>(answer.getText(), bolValue.getText());
								}
								return null;
							});
							final int count = i;
							Optional<Pair<String, String>> result = dialog.showAndWait();
							result.ifPresent(answerbol -> { // if result are present then execute
								System.out.println(
										"Username=" + answerbol.getKey() + ", Password=" + answerbol.getValue());
								answers[count] = answerbol.getKey();
								bolVal[count] = Boolean.parseBoolean(answerbol.getValue());
							});

							System.out.println(answers[i] + "\\" + bolVal[i]);

						} // end of for loop

						try {
							MySetClass<Answer> allAnswersForClosedQuestion = Program
									.getAllAnswersForClosedQuestion(answers, bolVal);
							managerClass.addQuestionAndAnswer(examRepository, questionText, allAnswersForClosedQuestion,
									ch, answerofOptionsInt, null);
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					}
				}

			} else { // else execute if character not from the given character
				JOptionPane.showMessageDialog(null, "You didn't Select the Character from C/c or O/o");
			}
			break;

		case 3:
			// String s11 = JOptionPane.showInputDialog("Please choose a question index to
			// update");
			// int index = Program.getIndexOfQuestion1(s11);
			// if (index != 0) {
			// String newWordingText = JOptionPane.showInputDialog("Please enter the new
			// text for the question");
			// try {
			// managerClass.updateWordingForQuestion(examRepository, index, newWordingText);
			// JOptionPane.showMessageDialog(null, "Question updated sucessfully ");
			// } catch (Exception e) {
			// JOptionPane.showMessageDialog(null, "ERRORR ! Question not Updated ");
			// }
			// } else {
			// JOptionPane.showMessageDialog(null, "ERRORR ! Index cannot be less than 1");
			// }

			c = new UpdateTheWordingOfAnExistingQuestionsCommand(examRepository,managerClass);
			break;

		case 4:
			// String s111 = JOptionPane.showInputDialog("Please choose a question index to
			// update");
			// int indexOfAnswers = 0;
			// int index1 = Integer.parseInt(String.valueOf(s111.charAt(0)));
			// String newWording = JOptionPane.showInputDialog(" Please enter the new text
			// for the answer");
			// if (managerClass.isTheQuestionInIndexClosedQuestion(examRepository, index1 -
			// 1)) {
			// String indexOfAnswerSt = JOptionPane.showInputDialog(" Please enter the new
			// text for the answer");
			// indexOfAnswers = Integer.parseInt(indexOfAnswerSt);
			// }
			// try {
			// managerClass.updateWordingOfAnswer(examRepository, indexOfAnswers,
			// newWording, index1 - 1);
			// } catch (Exception e) {
			// System.out.println(e.getMessage());
			// }

			// JOptionPane.showMessageDialog(null, "ERRORR ! Answer not updated");

			c = new UpdateTheWordingOfAnExistingAnswerCommand(examRepository,managerClass);
			break;

		case 5:
			try {
				String s1111 = JOptionPane.showInputDialog("Please choose a question index to update");
				int indexOfAnswer = 0;
				int indexOfQuestion = Integer.parseInt(String.valueOf(s1111.charAt(0)));
				if (managerClass.isTheQuestionInIndexClosedQuestion(examRepository, indexOfQuestion - 1)) {
					indexOfAnswer = Integer.parseInt(
							JOptionPane.showInputDialog(" Please enter the index of answer you want to delete"));
					managerClass.deleteAnswerFromQuestion(examRepository, indexOfQuestion, indexOfAnswer);
				} else {
					managerClass.deleteAnswerFromQuestion(examRepository, indexOfQuestion, 0);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "ERRORR ! Answer not deleted");
			}
			break;

		case 6:
			int numOfQuestions = Integer
					.parseInt(JOptionPane.showInputDialog("How many questions do you want in the exam?"));
			int[] numofQuestionsarr = new int[numOfQuestions];
			for (int i = 0; i < numofQuestionsarr.length; i++) {
				int questionPlace = Integer
						.parseInt(JOptionPane.showInputDialog("Enter Place for the Question)" + (i + 1)));
				if (questionPlace <= numofQuestionsarr.length) {
					numofQuestionsarr[i] = questionPlace;
				} else {
					JOptionPane.showMessageDialog(null, "ERRORR ! Value Greater than " + numofQuestionsarr.length);
					return;

				}
			}
			examByUser = managerClass.createExamManually(examRepository, numofQuestionsarr);
			passData.setExamrepos(examByUser);
			break;

		case 7:
			int numOfQuestions1 = Integer
					.parseInt(JOptionPane.showInputDialog("How many questions do you want in the exam?"));
			automaticExam = managerClass.createExamEutomatic(examRepository, numOfQuestions1);
			String name;
			automaticExam.save(null);
			passData.setExamrepos(automaticExam); // Passing object to PassData
			break;

		case 8:
			ExamRepository newE = null;
			if (automaticExam != null) {
				newE = ExamRepository.clone(automaticExam);
			} else if (examByUser != null) {
				newE = ExamRepository.clone(examByUser);
			} else
				newE = ExamRepository.clone(examRepository);
			passData pd = new passData();
			pd.setExamrepos(newE); // Passing object to PassData
			break;

		case 9:
			AnchorPane ap11 = new AnchorPane();
			Stage stage1 = new Stage();
			Scene scene1 = new Scene(ap11, 800, 600);
			stage1.setScene(scene1);
			stage1.show();
			TextArea ta1 = new TextArea();
			ta1.setPrefWidth(700);
			ta1.setPrefHeight(500);
			ap11.minHeight(800);
			ap11.getChildren().clear();
			ap11.getChildren().add(ta1);
			// Set<Question> ts = new TreeSet<Question>();
			// ts = (Set<Question>)
			// managerClass.convertFromArrayListToCollectionTreeSet(examRepository.getAllQuestion());
			// newTs = (TreeSet<Question>) ((TreeSet<Question>) ts).descendingSet(); // sort

			String t = managerClass.printSetWithIterator(newTs);
			ta1.setText(t);
			break;

		case 10:
			AnchorPane ap111 = new AnchorPane();
			Stage stage11 = new Stage();
			Scene scene11 = new Scene(ap111, 800, 600);
			stage11.setScene(scene11);
			stage11.show();
			TextArea ta11 = new TextArea();
			ta11.setPrefWidth(700);
			ta11.setPrefHeight(500);
			ap111.minHeight(800);
			ap111.getChildren().clear();
			ap111.getChildren().add(ta11);

			// Set<Question> ts1 = new TreeSet<Question>();
			// ts1 = (Set<Question>)
			// managerClass.convertFromArrayListToCollectionTreeSet(examRepository.getAllQuestion());
			// newTs = (TreeSet<Question>) ((TreeSet<Question>) ts1).descendingSet(); //
			// sort

			Set<Question> hashSetToTreeSet = new HashSet<>();
			hashSetToTreeSet = (Set<Question>) managerClass.convertFromTreeSetToHashSet(newTs);

			String t1 = managerClass.printSetWithIterator(hashSetToTreeSet);
			ta11.setText(t1);
			break;

		// part B
		case 11:
			AnchorPane ap1111 = new AnchorPane();
			Stage stage111 = new Stage();
			Scene scene111 = new Scene(ap1111, 800, 600);
			stage111.setScene(scene111);
			stage111.show();
			TextArea ta111 = new TextArea();
			ta111.setPrefWidth(700);
			ta111.setPrefHeight(500);
			ap1111.minHeight(800);
			ap1111.getChildren().clear();
			ap1111.getChildren().add(ta111);

			// without duplicate
			MyArrayList<Question> newArrList = new MyArrayList<Question>();
			newArrList = managerClass.toConveyFromCollectionToMyArrayList(hashSetToTreeSet1);
			// String t2 = managerClass.printSetWithIterator(newTs);
			String t2 = managerClass.printMyArrListWithIteratorDesignPattern(newArrList);
			ta111.setText(t2);
			break;

		case 12:
			AnchorPane ap11111 = new AnchorPane();
			Stage stage1111 = new Stage();
			Scene scene1111 = new Scene(ap11111, 800, 600);
			stage1111.setScene(scene1111);
			stage1111.show();
			TextArea ta1111 = new TextArea();
			ta1111.setPrefWidth(700);
			ta1111.setPrefHeight(500);
			ap11111.minHeight(800);
			ap11111.getChildren().clear();
			ap11111.getChildren().add(ta1111);

			String txt1 = "\n\n\n Print With Iterator Design Pattern\n\n";
			String toPrint1 = managerClass.printMyArrListWithIteratorDesignPattern(arrListWithDesPat1);
			String txt2 = "\n\n Print With Java Iterator\n\n";
			ArrayList<Question> newArrListJava = managerClass.convertFromMyArrListToJavaArrList(arrListWithDesPat1);
			String toPrint2 = managerClass.printMyArrListWithJavaIterator(newArrListJava);

			ta1111.setText(txt1 + toPrint1 + txt2 + toPrint2);

			break;

		case 13: // remove with java iter
			AnchorPane ap111111 = new AnchorPane();
			Stage stage11111 = new Stage();
			Scene scene11111 = new Scene(ap111111, 800, 600);
			stage11111.setScene(scene11111);
			stage11111.show();
			TextArea ta11111 = new TextArea();
			ta11111.setPrefWidth(700);
			ta11111.setPrefHeight(500);
			ap111111.minHeight(800);
			ap111111.getChildren().clear();
			ap111111.getChildren().add(ta11111);

			String str1 = "\nRemove with MyArrayList iterator\n\n";
			managerClass.removeTheLastEleWithMyIterator(arrListWithDesPat1);
			String toSowRemove = managerClass.printMyArrListWithIteratorDesignPattern(arrListWithDesPat1);

			String str2 = "\n\nRemove with Java iterator\n\n";
			ArrayList<Question> toShowWithArrList = new ArrayList<>();

			toShowWithArrList = managerClass.convertFromMyArrListToJavaArrList(arrListWithDesPat2);
			managerClass.removeTheLastEleWithJavaIterator(toShowWithArrList);

			String toShowWithJavaIter = managerClass.printMyArrListWithJavaIterator(toShowWithArrList);
			ta11111.setText(str1 + toSowRemove + str2 + toShowWithJavaIter);
			break;

		case 14:// print with button
			AnchorPane ap1111111 = new AnchorPane();
			Stage stage111111 = new Stage();
			Scene scene111111 = new Scene(ap1111111, 800, 600);
			stage111111.setScene(scene111111);
			stage111111.show();
			TextArea ta111111 = new TextArea();
			ta111111.setPrefWidth(700);
			ta111111.setPrefHeight(500);
			ap1111111.minHeight(800);
			ap1111111.getChildren().clear();
			ap1111111.getChildren().add(ta111111);

			MyArrayList<Question> newArrListForButton = new MyArrayList<Question>();
			newArrListForButton = managerClass.toConveyFromCollectionToMyArrayList(hashSetToTreeSet1);

			// MyLabel
			MyLevel l = new MyLevel();
			newArrListForButton.attach(l);

			AnchorPane.setLeftAnchor(l, 0.0);
			AnchorPane.setRightAnchor(l, 0.0);
			ap1111111.getChildren().add(l);

			MyButton b = new MyButton();
			newArrListForButton.attach(b);

			Iterator<Question> it = newArrListForButton.iterator();
			ap1111111.getChildren().add(b);
			managerClass.clickOnButton(b, it, ta111111);
			break;

		case 15: // remove with button
			AnchorPane ap11111111 = new AnchorPane();
			Stage stage1111111 = new Stage();
			Scene scene1111111 = new Scene(ap11111111, 800, 600);
			stage1111111.setScene(scene1111111);
			stage1111111.show();
			TextArea ta1111111 = new TextArea();
			ta1111111.setPrefWidth(700);
			ta1111111.setPrefHeight(500);
			ap11111111.minHeight(800);
			ap11111111.getChildren().clear();
			ap11111111.getChildren().add(ta1111111);

			MyArrayList<Question> newArrListForButton1 = new MyArrayList<Question>();
			newArrListForButton = managerClass.toConveyFromCollectionToMyArrayList(hashSetToTreeSet1);

			// MyLabel
			MyLevel l1 = new MyLevel();
			newArrListForButton.attach(l1);

			AnchorPane.setLeftAnchor(l1, 0.0);
			AnchorPane.setRightAnchor(l1, 0.0);
			ap11111111.getChildren().add(l1);

			MyButton b1 = new MyButton();
			newArrListForButton.attach(b1);
			Iterator<Question> it1 = newArrListForButton.iterator();
			ap11111111.getChildren().add(b1);
			managerClass.clickOnButtonToRemove(b1, it1, ta1111111);
			break;
			
		case 16:  			//memnto design pattern
			AnchorPane ap111111111 = new AnchorPane();
			Stage stage11111111 = new Stage();
			Scene scene11111111 = new Scene(ap111111111, 800, 600);
			stage11111111.setScene(scene11111111);
			stage11111111.show();
			TextArea ta11111111 = new TextArea();
			ta11111111.setPrefWidth(700);
			ta11111111.setPrefHeight(500);
			ap111111111.minHeight(800);
			ap111111111.getChildren().clear();
			ap111111111.getChildren().add(ta11111111);
			
			//ArrayList<Question> allQ = examRepository.getAllQuestion();
			MySetClass<Answer> answersSetQ3 = new MySetClass<>();
			answersSetQ3.add(new Answer("Uganda", true));
			answersSetQ3.add(new Answer("Israel", false));
			answersSetQ3.add(new Answer("Spain", false));
			answersSetQ3.add(new Answer("Morocco", false));
			answersSetQ3.add(new Answer("England", false));
			answersSetQ3.add(new Answer("China", true));
			answersSetQ3.add(new Answer("Japan", true));
			answersSetQ3.add(new Answer("Canada", true));
			
			String s11= "\n\nBefore Memento\n\n";
			String s22 = "\n\nAfter Memento\n\n";
			String toShowSetQ3BeforeMem = answersSetQ3.toString();			
			//ta11111111.setText();
			answersSetQ3.createMementoArr();
			answersSetQ3.setMementoArr();
			ta11111111.setText(s11 + toShowSetQ3BeforeMem + s22 + answersSetQ3.toString());
			break;


		case 17:
			ExamRepository.saveToBinaryFile(examRepository);
			// JOptionPane.showMessageDialog(null, "Bye Bye ..");
			JOptionPane.showConfirmDialog(null, System.getProperty("user.dir"));
			break;

		default:
			ExamRepository.saveToBinaryFile(examRepository);
			JOptionPane.showMessageDialog(null, "Bye Bye ..");
			break;
		}
		if(c!=null)
			c.execute();

	}
}
