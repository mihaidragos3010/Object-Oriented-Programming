package com.example.project;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Question {

    //Arguments
    private User user;
    private String type;
    private String text;
    private ArrayList<Answer> answers;
    private static int nrQuestions=0;
    private int ID;

    //Builders
    Question(User user,String text,String type,final String[] args){
//        This is the main builder to create a question object and pot his data in Questions file
        this.user=user;
        this.type=type;
        this.text=text;
        nrQuestions++;
        this.ID=nrQuestions;
        this.answers = new ArrayList<Answer>();
        for(int i=5;i< args.length;i=i+2){
            String text_answer;
            int value_answer;
            text_answer = args[i].substring(11, args[i].length()-1);;
            value_answer = Integer.parseInt(args[i+1].substring(22, args[i+1].length() - 1));
            Answer answer = new Answer(text_answer,value_answer);
            answers.add(answer);
        }
    }

    Question(User user,String text){
//        This is a secondary builder used to called get functions
        this.user=user;
        this.text=text;
    }


    //Methods
    public int getID() {
        return ID;
    }
    public String getText() {
        return text;
    }
    public String getType() {
        return type;
    }
    public static void resetQuestionsID(){nrQuestions = 0;}
    public void createQuestion(){
//        Method creat and write a question data in a file

        try (FileWriter fw = new FileWriter("./src/main/java/com/example/project/Questions.csv", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)) {

            out.print(this.ID);
            out.print(",");
            out.print(this.user.getName());
            out.print(",");
            out.print(this.user.getPassword());
            out.print(",");
            out.print(this.text);
            out.print(",");
            out.print(this.type);
            out.print(",");
            for(int i=0;i< answers.size();i++){
                if(i == answers.size()-1){
                    out.print(answers.get(i).getID());
                    out.print(",");
                    out.print(answers.get(i).getText());
                    out.print(",");
                    out.println(answers.get(i).getValue());
                    break;
                }
                out.print(answers.get(i).getID());
                out.print(",");
                out.print(answers.get(i).getText());
                out.print(",");
                out.print(answers.get(i).getValue());
                out.print(",");
            }

            System.out.println("{ 'status' : 'ok', 'message' : 'Question added successfully'}");

        } catch (IOException e) {
            //exception handling left as an exercise for the reader
            System.out.println("Question write doen't work");
        }
    }

    public void getQuestion(){
//        Method step through line by line and show on the screen required details by right question

        int found = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(("./src/main/java/com/example/project/Questions.csv")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] file_question;
                file_question = line.split(",");
                if(text.equals(file_question[3])) {
                    found = 1;
                    System.out.println("{ 'status' : 'ok', 'message' : '" + file_question[0] + "'}");
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println();
        }

        if(found == 0)
            System.out.println("{ 'status' : 'error', 'message' : 'Question does not exist'}");
    }

    public void getAllQuestion(){
//        Method step through Questions file line by line and check the user_command is equals with user_file the
//        required data(question id, question name) in a String list to show after on the screen. If every user_file
//        is different from the user_command the method show a right message on the screen

        int found = 0;
        ArrayList<String> listGetAllQuestions = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(("./src/main/java/com/example/project/Questions.csv")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] file_question;
                file_question = line.split(",");
                User file_user = new User(file_question[1],file_question[2]);
                if(user.equals(file_user)) {
                    found = 1;
                    listGetAllQuestions.add(file_question[0]);
                    listGetAllQuestions.add(file_question[3]);
                }
            }
        } catch (Exception e) {
            System.out.println();
        }

        if(found == 1){
            System.out.print("{ 'status' : 'ok', 'message' : '[");
            for(int i=0; i < listGetAllQuestions.size(); i=i+2){
                System.out.print("{\"question_id\" : \""+ listGetAllQuestions.get(i) +"\", \"question_name\" : \""+ listGetAllQuestions.get(i+1) +"\"}");
                if(i != listGetAllQuestions.size()-2)
                    System.out.print(", ");
            }
            System.out.println("]'}");
        }
        if(found == 0)
            System.out.println("{ 'status' : 'error', 'message' : 'Question does not exist'}");

    }
}

