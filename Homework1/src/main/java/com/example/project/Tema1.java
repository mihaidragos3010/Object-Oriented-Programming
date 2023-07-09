package com.example.project;
import java.io.File;
import static com.example.project.Check.*;

public class Tema1 {

	//Method delete files and reset the id counter for each class
	static void cleanup_all(){
		Question.resetQuestionsID();
		Quizz.resetQuizzID();
		Answer.resetAnswersID();
		File fileUsers= new File("./src/main/java/com/example/project/Users.csv");
		fileUsers.delete();
		File fileQuestions= new File("./src/main/java/com/example/project/Questions.csv");
		fileQuestions.delete();
		File fileQuizz= new File("./src/main/java/com/example/project/Quizz.csv");
		fileQuizz.delete();
		File fileSolutons= new File("./src/main/java/com/example/project/Solutions.csv");
		fileSolutons.delete();
	}
	public static void main(final String[] args)
	{
//		In every check I try to see if the command fits and after I check if every argument are follow the correct syntax.

		if(args != null) {
			String command = args[0];
			if (command.equals("-create-user")) {

				if (checkCreateUser(args)) {
					String name = args[1].substring(4, args[1].length() - 1);
					String password = args[2].substring(4, args[2].length() - 1);
					User user = new User(name, password);

					user.createUser();
				}
			}

			if (command.equals("-create-question")) {
				if (checkCreateQuestion(args)) {
					String name = args[1].substring(4, args[1].length() - 1);
					String password = args[2].substring(4, args[2].length() - 1);
					String text = args[3].substring(7,args[3].length() - 1);
					String type = args[4].substring(7,args[4].length() - 1);
					User user = new User(name,password);
					Question question = new Question(user,text,type,args);

					question.createQuestion();
				}
			}

			if (command.equals("-get-question-id-by-text")) {
				if (checkGetQuestion(args)) {
					String name = args[1].substring(4, args[1].length() - 1);
					String password = args[2].substring(4, args[2].length() - 1);
					String text = args[3].substring(7,args[3].length() - 1);
					User user = new User(name,password);
					Question question = new Question(user,text);

					question.getQuestion();
				}
			}

			if (command.equals("-get-all-questions")) {
				if (checkGetAllQuestion(args)) {
					String name = args[1].substring(4, args[1].length() - 1);
					String password = args[2].substring(4, args[2].length() - 1);
					String text = null;
					User user = new User(name,password);
					Question question = new Question(user,text);

					question.getAllQuestion();
				}
			}

			if (command.equals("-create-quizz")) {
				if (checkCreateQuizz(args)) {
					String username = args[1].substring(4, args[1].length() - 1);
					String password = args[2].substring(4, args[2].length() - 1);
					String name = args[3].substring(7, args[3].length() - 1);
					User user = new User(username,password);
					Quizz quizz = new Quizz(user,name,args);

					quizz.createQuizz();
				}
			}

			if (command.equals("-get-quizz-by-name")) {
				if (checkGetQuizz(args)) {
					String username = args[1].substring(4, args[1].length() - 1);
					String password = args[2].substring(4, args[2].length() - 1);
					String name = args[3].substring(7,args[3].length() - 1);
					User user = new User(username,password);
					Quizz quizz = new Quizz(user,name,args);

					quizz.getQuizz();
				}
			}

			if (command.equals("-get-all-quizzes")) {
				if (checkGetAllQuizz(args)) {
					String username = args[1].substring(4, args[1].length() - 1);
					String password = args[2].substring(4, args[2].length() - 1);
					User user = new User(username,password);
					Quizz quizz = new Quizz(user);

					quizz.getAllQuizz();
				}
			}

			if (command.equals("-get-quizz-details-by-id")) {
				if (checkGetQuizzDetails(args)) {
					String username = args[1].substring(4, args[1].length() - 1);
					String password = args[2].substring(4, args[2].length() - 1);
					int idQuizz =  Integer.parseInt(args[3].substring(5, args[3].length() - 1));
					User user = new User(username,password);
					Quizz quizz = new Quizz(user);

					quizz.getQuizzDetails(idQuizz);
				}
			}

			if (command.equals("-submit-quizz")) {
				if (checkSubmitQuizz(args)) {
					String username = args[1].substring(4, args[1].length() - 1);
					String password = args[2].substring(4, args[2].length() - 1);
					String idQuizz =  args[3].substring(10, args[3].length() - 1);
					User user = new User(username,password);
					Quizz quizz = new Quizz(user);

					quizz.submitQuizz(idQuizz,args);
				}
			}

			if (command.equals("-delete-quizz-by-id")) {
				if (checkDeleteQuizz(args)) {
					String username = args[1].substring(4, args[1].length() - 1);
					String password = args[2].substring(4, args[2].length() - 1);
					String idQuizz =  args[3].substring(5, args[3].length() - 1);
					User user = new User(username,password);
					Quizz quizz = new Quizz(user);

					quizz.deleteQuizz(idQuizz); // nu merge
				}
			}

			if (command.equals("-get-my-solutions")) {
				if (checkGetMySolutions(args)) {
					String username = args[1].substring(4, args[1].length() - 1);
					String password = args[2].substring(4, args[2].length() - 1);
					User user = new User(username,password);

					user.getMySolutions();
				}
			}

			if(command.equals("-cleanup-all")){
				cleanup_all();
			}
		}
		else {
			System.out.println("Hello world!");
		}

	}
}
