package com.example.project;

import java.io.*;
import java.util.ArrayList;

public class Quizz {

    private User user;
    private String name;
    private static int nrQuizz=0;
    private String[] questions;
    private int ID;

    public Quizz(User user,String name,final String[] args) {
//        This is the main builder used to create a quizz object and write required data in Quizz file
        this.user = user;
        this.name = name;
        questions = new String[args.length-4];
        for(int i=0;i<args.length-4;i++){
            questions[i] = args[i+4].substring(13,args[i+4].length()-1);
        }
        nrQuizz++;
        ID = nrQuizz;
    }

    public Quizz(User user) {
//        This is the secondary builder used to call get data function from Quizz file
        this.user = user;
    }

    public void createQuizz(){
//        Method create and write in a Quizz file
        try (FileWriter fw = new FileWriter("./src/main/java/com/example/project/Quizz.csv", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            out.print(ID);
            out.print(",");
            out.print(user.getName());
            out.print(",");
            out.print(user.getPassword());
            out.print(",");
            out.print(name);
            out.print(",");
            for(int i=0;i<questions.length;i++){
                if(i == questions.length-1){
                    out.print(questions[i]);
                    out.println();
                    break;
                }
                out.print(questions[i]);
                out.print(",");
            }

            System.out.println("{ 'status' : 'ok', 'message' : 'Quizz added succesfully'}");

        } catch (IOException e) {
            //exception handling left as an exercise for the reader
            System.out.println("Question write doesn't work");
        }
    }

    public static void resetQuizzID(){nrQuizz = 0;}

    public void getQuizz(){
//        Method step through Quizz file line by line and show on the screen Quizz data from the quizz in a file with
//        the same name

        int found = 0;
        File f = new File("./src/main/java/com/example/project/Quizz.csv");
        try (BufferedReader br = new BufferedReader(new FileReader(("./src/main/java/com/example/project/Quizz.csv")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] file_quizz;
                file_quizz = line.split(",");
                if(name.equals(file_quizz[3])) {
                    found = 1;
                    System.out.println("{ 'status' : 'ok', 'message' : '"+ file_quizz[0] + "'}");
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println();
        }

        if(found == 0)
            System.out.println("{ 'status' : 'error', 'message' : 'Quizz does not exist'}");
    }

    public void getAllQuizz(){
//        Method compare user_command with each quizz user and if these are the same put in String list the details of
//        that quizz. After every list data show on the screen

        int found = 0;
        ArrayList<String> listGetAllQuizz = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(("./src/main/java/com/example/project/Quizz.csv")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] file_quizz;
                file_quizz = line.split(",");
                User file_user = new User(file_quizz[1],file_quizz[2]);
                if(user.equals(file_user)) {
                    found = 1;
                    listGetAllQuizz.add(file_quizz[0]);
                    listGetAllQuizz.add(file_quizz[3]);
                }
            }
        } catch (Exception e) {
            System.out.println();
        }

        if(found == 1){
            System.out.print("{ 'status' : 'ok', 'message' : '[");
            for(int i=0; i < listGetAllQuizz.size(); i=i+2){
                System.out.print("{\"quizz_id\" : \""+listGetAllQuizz.get(i)+"\", \"quizz_name\" : \""+listGetAllQuizz.get(i+1)+"\", \"is_completed\" : \"False\"}");
                if(i != listGetAllQuizz.size()-2)
                    System.out.print(", ");
            }
            System.out.println("]'}");
        }

        if(found == 0)
            System.out.println("{ 'status' : 'error', 'message' : 'Question does not exist'}");

    }


    public void getQuizzDetails(Integer ID){
//          Method check if the quizz id can fine on Quizz file. If so open the Questions file and search every
//          questions id and show his data on the screen.
        try (BufferedReader brQuizz = new BufferedReader(new FileReader(("./src/main/java/com/example/project/Quizz.csv")))) {
            String line_quizz;
            System.out.print("{'status':'ok','message':'[");
            while ((line_quizz = brQuizz.readLine()) != null) {
                String[] file_quizz;
                file_quizz = line_quizz.split(",");
                if(ID.equals(Integer.parseInt(file_quizz[0]))) {

                    for(int i=0;i<file_quizz.length-4;i++){  //This for get each id question from this quizz and search
                        String question_id = file_quizz[i+4];//on Questions file
                        try (BufferedReader brQuestion = new BufferedReader(new FileReader(("./src/main/java/com/example/project/Questions.csv")))) {
                            String line;
                            while ((line = brQuestion.readLine()) != null) {
                                String[] file_question;
                                file_question = line.split(",");
                                if(question_id.equals(file_question[0])) {//de modificat aici index pentru answers si pus for pentru intrebari cu rasp multiplu
                                    System.out.print("{\"question-name\":\""+file_question[3]+"\", \"question_index\":\""+(i+1)+"\", \"question_type\":\""+file_question[4]+"\", \"answers\":\"[{\"answer_name\":\""+file_question[6]+"\", \"answer_id\":\""+file_question[5]+"\"}, {\"answer_name\":\""+file_question[9]+"\", \"answer_id\":\""+file_question[8]+"\"}]\"}");
                                    if(i!=file_quizz.length-5)
                                        System.out.print(", ");
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            System.out.println();
                        }
                    }

                    System.out.println("]'}");
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println();
        }
    }

    public void deleteQuizz(String idQuizz){
//        Method search all quizz are different by quizz id on the command and after put that on the String list.
//        In the end delete the quizz file and write again this file with data from String list
        File fr = new File("./src/main/java/com/example/project/Quizz.csv");
        ArrayList<String> quizz = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(("./src/main/java/com/example/project/Quizz.csv")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] file_quizz;
                file_quizz = line.split(",");
                if(idQuizz.equals(file_quizz[0]) == false) {
                    quizz.add(line);
                }
            }
        } catch (Exception e) {
            System.out.println();
        }

        fr.delete();
        try (FileWriter fw = new FileWriter("./src/main/java/com/example/project/Quizz.csv", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            for(int i=0;i < quizz.size();i++){

                out.println(quizz.get(i));
            }

            System.out.println("{ 'status' : 'ok', 'message' : 'Quizz deleted successfully'}");

        } catch (IOException e) {
            //exception handling left as an exercise for the reader
            System.out.println("Question write doen't work");
        }

    }

    public void submitQuizz(String idQuizz,final String[] args){

//      Method search percent of total 100 points have each question of quizz. For each question search every percent
//      of point by every answer and put all this data in a Float list. In the end get every index from command answers
//      and put in a "sum" variable. For example, in a quizz with 3 questions which one is 100/nrQuestions(3) = 33,3p.
//      For a question with: 2 answers correct these will have 33,3p/nrAnswerCorrect(2) = 16,65p and 3 answers wrong
//      these will have 33,3p/nrAnswerWrong(3) = -11,1p. All this results will put in a Float list and add late in the sum.

        ArrayList<Float> answers = new ArrayList<Float>();
        answers.add(0f);
        try (BufferedReader br_quizz = new BufferedReader(new FileReader(("./src/main/java/com/example/project/Quizz.csv")))) {
            String line_quizz;
            while ((line_quizz = br_quizz.readLine()) != null) {
                String[] file_quizz;
                file_quizz = line_quizz.split(",");
                if(idQuizz.equals(file_quizz[0])) {
                    int nrQuestions = file_quizz.length-4;
                    float pointQuestion = 0.00f;
                    pointQuestion = (100 / (float)nrQuestions);
                    for(int i=4;i<file_quizz.length;i++){
                        try (BufferedReader br_question = new BufferedReader(new FileReader(("./src/main/java/com/example/project/Questions.csv")))) {
                            String line_question;
                            while ((line_question = br_question.readLine()) != null) {
                                String[] file_question;
                                file_question = line_question.split(",");
                                int nrAnswers = (file_question.length - 5)/3;
                                if(file_quizz[i].equals(file_question[0])) {
                                    int nrAnswerCorrect=0;
                                    int nrAnswerWrong=0;
                                    for(int j=7;j< file_question.length;j=j+3){
                                        if(file_question[j].equals("true"))
                                            nrAnswerCorrect++;
                                        if(file_question[j].equals("false"))
                                            nrAnswerWrong++;
                                    }

                                    for(int j=5;j< file_question.length;j=j+3){
                                        if(file_question[j+2].equals("true")){
                                            float points = 0.00f;
                                            points = pointQuestion/nrAnswerCorrect;
                                            answers.add(points);
                                        }

                                        if(file_question[j+2].equals("false")){
                                            float points = 0.00f;
                                            points = -(pointQuestion/nrAnswerWrong);
                                            answers.add(points);
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println();
                        }
                    }
                    break;
                }
            }

            float sum = 0.00f;
            for(int i=4;i<args.length;i++) {
                int index =Integer.parseInt(args[i].split("'")[1]);
                sum = sum + answers.get(index);
            }

            sum = Math.round(sum);
            if(sum < 0)
                sum = 0;
            System.out.println("{ 'status' : 'ok', 'message' : '"+(int)sum+" points'}");

            //Creat solutions file
            try (FileWriter fw = new FileWriter("./src/main/java/com/example/project/Solutions.csv", true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {

                String nameQuizz = null;
                try (BufferedReader br = new BufferedReader(new FileReader(("./src/main/java/com/example/project/Quizz.csv")))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] file_quizz;
                        file_quizz = line.split(",");
                        if(idQuizz.equals(file_quizz[0])) {
                            nameQuizz = file_quizz[3];
                        }
                    }
                } catch (Exception e) {}

                out.print(user.getName());
                out.print(",");
                out.print(user.getPassword());
                out.print(",");
                out.print(idQuizz);
                out.print(",");
                out.print(nameQuizz);
                out.print(",");
                out.println(sum);

            } catch (IOException e) {System.out.println("Question write doen't work");}


        } catch (Exception e) {
            System.out.println();
        }

    }
}

