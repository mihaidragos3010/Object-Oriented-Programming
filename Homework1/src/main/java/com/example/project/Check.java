package com.example.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
//  This is a class which contains every check function
public class Check {
    static boolean checkCreateUser(final String[] args){

        String name;
        String password;
        File f = new File("./src/main/java/com/example/project/Users.csv");
//        In this piece of code I try to put a data argument in a variable and if doesn't exist
//        will generate an exception that I will solve

//        Username exist check
        try {
            name = args[1].substring(4, args[1].length() - 1);
            if(args[1].charAt(1)!='u') {
                System.out.print("{ 'status' : 'error', 'message' : 'Please provide username'}");
                return false;
            }
        } catch (Exception e) {
            System.out.print("{ 'status' : 'error', 'message' : 'Please provide username'}");
            return false;
        }

//        Password exist check
        try{
            password = args[2].substring(4, args[2].length() - 1);
            if(args[2].charAt(1)!='p') {
                System.out.print("{ 'status' : 'error', 'message' : 'Please provide username'}");
                return false;
            }
        }catch (Exception e){
            System.out.print("{ 'status' : 'error', 'message' : 'Please provide password'}");
            return false;
        }

//        Check if the user file exist and after I go through line by line and check if exist a user with the same name and password like
//        the one introduced from the arguments
        if(!f.exists())
            return true;
        try (BufferedReader br = new BufferedReader(new FileReader(("./src/main/java/com/example/project/Users.csv")))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(name.length()+password.length()<line.length()){
                    String file_name=line.substring(0,name.length());
                    String file_password=line.substring(name.length()+1,line.length());
                    if(name.equals(file_name) && password.equals(file_password)) {
                        System.out.print("{ 'status' : 'error', 'message' : 'User already exists'}");
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Read user not work");
        }


        return true;
    }

    static boolean checkCreateQuestion(final String[] args){
        String username;
        String password;
        String type;
        String text;
        ArrayList<Answer> answers = new ArrayList<Answer>(5);

        //Username exist check
        try{
            username = args[1].substring(4, args[1].length()-1);
            if(args[1].charAt(1)!='u') {
                System.out.println("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
                return false;
            }
        }catch (Exception e){
            System.out.println("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
            return false;
        }
        //Password exist check
        try{
            password = args[2].substring(4, args[2].length()-1);
            if(args[2].charAt(1)!='p') {
                System.out.println("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
                return false;
            }
        }catch (Exception e){
            System.out.println("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
            return false;
        }

        User question_user = new User(username,password);

        //FileUsers exist check
        File fileUsers = new File("./src/main/java/com/example/project/Users.csv");
        if(fileUsers.exists() == false) {
            System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
            return false;
        }

        //Username can't be found
        try (BufferedReader br = new BufferedReader(new FileReader(("./src/main/java/com/example/project/Users.csv")))) {
            String line;
            int founduser=0;
            while ((line = br.readLine()) != null) {
                User file_user= new User(line.split(",")[0], line.split(",")[1]);
                if(question_user.equals(file_user)){
                    founduser=1;
                }
            }
            if(founduser == 0){
                System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
                return false;
            }
        } catch (IOException e) {
            System.out.println("Read user not work");
        }


//        Text question doesn't exist
        try {
            text = args[3].substring(7,args[3].length()-1);
            if(args[3].substring(1,5).equals("text") == false){
                System.out.println("{ 'status' : 'error', 'message' : 'No question text provided'}");
                return	false;
            }

        }catch (Exception e){
            System.out.println("{ 'status' : 'error', 'message' : 'No question text provided'}");
            return false;
        }

//        Type question doesn't exist
        try {
            type = args[4].substring(7,args[4].length()-1);
        }catch (Exception e){
            System.out.println("{ 'status' : 'error', 'message' : 'No question type provided'}");
            return false;
        }

//		Answer doesn't have text/value
        String answerText;
        String answerValue;
        int l;
        try{

            for(l=0;l<5;l++) {
                answerText = args[5+2*l];
                if(answerText.substring(0,11).equals("-answer-"+(l+1)+" '")==false){
                    System.out.println("{ 'status' : 'error', 'message' : 'Answer " + (l+1) + " has no answer description'}");
                    return false;
                }
                try{
                    answerValue = args[6+2*l];
                    if(answerValue.substring(13,20).equals("correct")==false){
                        System.out.println("{ 'status' : 'error', 'message' : 'Answer " + (l+1) + " has no answer description'}");
                        return false;
                    }
                }catch (Exception e){
                    System.out.println("{ 'status' : 'error', 'message' : 'Answer " + (l+1) + " has no answer correct flag'}");
                    return false;
                }
            }
        }catch (Exception e){

        }


//        No every answers
        int nrAnswer = 0;
        int y=0;
        try {
//            In this piece of code I try to put in an Answer data list text and value of each argument
            answers.add(new Answer(args[5].substring(11, args[5].length() - 1), Integer.parseInt(args[6].substring(22, args[6].length() - 1))));
            nrAnswer++;
            answers.add(new Answer(args[7].substring(11, args[7].length() - 1), Integer.parseInt(args[8].substring(22, args[8].length() - 1))));
            nrAnswer++;
            answers.add(new Answer(args[9].substring(11, args[9].length() - 1), Integer.parseInt(args[10].substring(22, args[10].length() - 1))));
            nrAnswer++;
            answers.add(new Answer(args[11].substring(11, args[11].length() - 1), Integer.parseInt(args[12].substring(22, args[12].length() - 1))));
            nrAnswer++;
            answers.add(new Answer(args[13].substring(11, args[13].length() - 1), Integer.parseInt(args[14].substring(22, args[14].length() - 1))));
            nrAnswer++;
            answers.add(new Answer(args[15].substring(11, args[15].length() - 1), Integer.parseInt(args[16].substring(22, args[16].length() - 1))));
            nrAnswer++;
//            If code get here those it means that the command have too many arguments
            if(nrAnswer > 5) {
                Answer.setNrAnswer(nrAnswer);
                System.out.println("{ 'status' : 'error', 'message' : 'More than 5 answers were submitted'}");
                return false;
            }
        }catch (Exception e) {
            Answer.setNrAnswer(nrAnswer);
//            I don't have any answers arguments
            if (nrAnswer == 0) {
                System.out.println("{ 'status' : 'error', 'message' : 'No answer provided'}");
                return false;
            }
//            I have one answer arguments
            if (nrAnswer == 1) {
                System.out.println("{ 'status' : 'error', 'message' : 'Only one answer provided'}");
                return false;
            }
        }

        //Questions single with more correct answers
        if(type.equals("single")) {
            int nrCorrectAnswers = 0;
            for (Answer a : answers) {
                if (a.getValue() == true)
                    nrCorrectAnswers++;
                if (nrCorrectAnswers > 1) {
                    System.out.println("{ 'status' : 'error', 'message' : 'Single correct answer question has more than one correct answer'}");
                    return false;
                }
            }
        }

        //More Answers same
        for (int i=0;i<nrAnswer-1;i++)
            for (int j=i+1;j<nrAnswer;j++)
                if (answers.get(i).equals(answers.get(j))) {
                    System.out.println("{ 'status' : 'error', 'message' : 'Same answer provided more than once'}");
                    return false;
                }

//        Question already exist
        File f = new File("./src/main/java/com/example/project/Questions.csv");
        try (BufferedReader br = new BufferedReader(new FileReader(("./src/main/java/com/example/project/Questions.csv")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] file_question;
                file_question = line.split(",");
                if(text.equals(file_question[3])) {
                    System.out.print("{ 'status' : 'error', 'message' : 'Question already exists'}");
                    return false;
                }
            }
        } catch (Exception e) {
            if(!f.exists())
                return true;
        }


        return true;
    }

    static boolean checkGetQuestion(final  String[] args){

        String name;
        String password;
        String text;

//        Username exist check
        try {
            name = args[1].substring(4, args[1].length() - 1);
            if(args[1].charAt(1)!='u') {
                System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
                return false;
            }
        } catch (Exception e) {
            System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
            return false;
        }

//        Password exist check
        try{
            password = args[2].substring(4, args[2].length() - 1);
            if(args[2].charAt(1)!='p') {
                System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
                return false;
            }
        }catch (Exception e){
            System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
            return false;
        }

        //Text exist check
        try {
            text = args[3].substring(7,args[3].length()-1);
            if(args[3].substring(1,5).equals("text") == false){
                System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
                return	false;
            }

        }catch (Exception e){
            System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
            return false;
        }
        User question_user = new User(name,password);

        //FileUsers exist check
        File fileUsers = new File("./src/main/java/com/example/project/Users.csv");
        if(fileUsers.exists() == false) {
            System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
            return false;
        }

        //Username can't be found in file
        try (BufferedReader br = new BufferedReader(new FileReader(("./src/main/java/com/example/project/Users.csv")))) {
            String line;
            int founduser=0;
            while ((line = br.readLine()) != null) {
                User file_user= new User(line.split(",")[0], line.split(",")[1]);
                if(question_user.equals(file_user)){
                    founduser=1;
                }
            }
            if(founduser == 0){
                System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
                return false;
            }
        } catch (IOException e) {
            System.out.println("Read user not work");
        }

        return true;
    }

    static boolean checkGetAllQuestion(final String[] args){

        String name;
        String password;

//        Username exist check
        try {
            name = args[1].substring(4, args[1].length() - 1);
            if(args[1].charAt(1)!='u') {
                System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
                return false;
            }
        } catch (Exception e) {
            System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
            return false;
        }

//        Password exist check
        try{
            password = args[2].substring(4, args[2].length() - 1);
            if(args[2].charAt(1)!='p') {
                System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
                return false;
            }
        }catch (Exception e){
            System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
            return false;
        }
        User question_user = new User(name,password);

        //FileUsers exist check
        File fileUsers = new File("./src/main/java/com/example/project/Users.csv");
        if(fileUsers.exists() == false) {
            System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
            return false;
        }

        //Username can't be found in file
        try (BufferedReader br = new BufferedReader(new FileReader(("./src/main/java/com/example/project/Users.csv")))) {
            String line;
            int founduser=0;
            while ((line = br.readLine()) != null) {
                User file_user= new User(line.split(",")[0], line.split(",")[1]);
                if(question_user.equals(file_user)){
                    founduser=1;
                }
            }
            if(founduser == 0){
                System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
                return false;
            }
        } catch (IOException e) {
            System.out.println("Read user not work");
        }

        return true;
    }

    static boolean checkCreateQuizz(final String[] args){


        String name;
        String password;

//        Username exist check
        try {
            name = args[1].substring(4, args[1].length() - 1);
            if(args[1].charAt(1)!='u') {
                System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
                return false;
            }
        } catch (Exception e) {
            System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
            return false;
        }

//        Password exist check
        try{
            password = args[2].substring(4, args[2].length() - 1);
            if(args[2].charAt(1)!='p') {
                System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
                return false;
            }
        }catch (Exception e){
            System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
            return false;
        }
        User quizz_user = new User(name,password);

        //FileUsers exist check
        File fileUsers = new File("./src/main/java/com/example/project/Users.csv");
        if(fileUsers.exists() == false) {
            System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
            return false;
        }

//        Username can't be found in file
        try (BufferedReader br = new BufferedReader(new FileReader(("./src/main/java/com/example/project/Users.csv")))) {
            String line;
            int founduser=0;
            while ((line = br.readLine()) != null) {
                User file_user= new User(line.split(",")[0], line.split(",")[1]);
                if(quizz_user.equals(file_user)){
                    founduser=1;
                }
            }
            if(founduser == 0){
                System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
                return false;
            }
        } catch (IOException e) {
            System.out.println("Read user not work");
        }

//          Check if every question id exist in file
        int nrQuestions = 0;
        String questionID;
        int found;
        for(int i=0;i<args.length-4;i=i+1) {
            questionID = args[i + 4].substring(13, args[i+4].length()-1);
            found = 0;

            try (BufferedReader br = new BufferedReader(new FileReader(("./src/main/java/com/example/project/Questions.csv")))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] file_question;
                    file_question = line.split(",");
                    if(questionID.equals(file_question[0])) {
                        found = 1;
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println();
            }

            if(found == 0) {
                System.out.println("{ 'status' : 'error', 'message' : 'Question ID for question " + (i + 1) + " does not exist'}");
                return false;
            }
            nrQuestions++;
        }
        if(nrQuestions > 10)
            System.out.println("{ 'status' : 'error', 'message' : Prea multe intrebari");

        //Quizz already exist
        String quizz_name = args[3].substring(7,args[3].length()-1);
        File f = new File("./src/main/java/com/example/project/Quizz.csv");
        try (BufferedReader br = new BufferedReader(new FileReader(("./src/main/java/com/example/project/Quizz.csv")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] file_question;
                file_question = line.split(",");
                if(quizz_name.equals(file_question[3])) {
                    System.out.print("{ 'status' : 'error', 'message' : 'Quizz name already exists'}");
                    return false;
                }
            }
        } catch (Exception e) {
            if(!f.exists())
                return true;
        }

        return true;
    }

    static boolean checkGetQuizz(final  String[] args){

        String username;
        String password;
        String name;

//        User exist check
        try {
            username = args[1].substring(4, args[1].length() - 1);
            if(args[1].charAt(1)!='u') {
                System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
                return false;
            }
        } catch (Exception e) {
            System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
            return false;
        }
//          Password exist check
        try{
            password = args[2].substring(4, args[2].length() - 1);
            if(args[2].charAt(1)!='p') {
                System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
                return false;
            }
        }catch (Exception e){
            System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
            return false;
        }

//        Text check
        try {
            name = args[3].substring(7,args[3].length()-1);
            if(args[3].substring(1,5).equals("name") == false){
                System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
                return	false;
            }
        }catch (Exception e){
            System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
            return false;
        }
        User question_user = new User(username,password);

        //FileUsers not exist
        File fileUsers = new File("./src/main/java/com/example/project/Users.csv");
        if(fileUsers.exists() == false) {
            System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
            return false;
        }

        //Username can't be found in file
        try (BufferedReader br = new BufferedReader(new FileReader(("./src/main/java/com/example/project/Users.csv")))) {
            String line;
            int founduser=0;
            while ((line = br.readLine()) != null) {
                User file_user= new User(line.split(",")[0], line.split(",")[1]);
                if(question_user.equals(file_user)){
                    founduser=1;
                }
            }
            if(founduser == 0){
                System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
                return false;
            }
        } catch (IOException e) {
            System.out.println("Read user not work");
        }

        return true;
    }

    static boolean checkGetAllQuizz(final String[] args){

        String name;
        String password;

//        Username exist check
        try {
            name = args[1].substring(4, args[1].length() - 1);
            if(args[1].charAt(1)!='u') {
                System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
                return false;
            }
        } catch (Exception e) {
            System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
            return false;
        }

//          Password exist check
        try{
            password = args[2].substring(4, args[2].length() - 1);
            if(args[2].charAt(1)!='p') {
                System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
                return false;
            }
        }catch (Exception e){
            System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
            return false;
        }

        User question_user = new User(name,password);
//        FileUsers not
        File fileUsers = new File("./src/main/java/com/example/project/Users.csv");
        if(fileUsers.exists() == false) {
            System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
            return false;
        }

//        Username can't be found
        try (BufferedReader br = new BufferedReader(new FileReader(("./src/main/java/com/example/project/Users.csv")))) {
            String line;
            int founduser=0;
            while ((line = br.readLine()) != null) {
                User file_user= new User(line.split(",")[0], line.split(",")[1]);
                if(question_user.equals(file_user)){
                    founduser=1;
                }
            }
            if(founduser == 0){
                System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
                return false;
            }
        } catch (IOException e) {
            System.out.println("Read user not work");
        }

        return true;
    }

    static boolean checkGetQuizzDetails(final String[] args){

        String name;
        String password;

//        Username exist check
        try {
            name = args[1].substring(4, args[1].length() - 1);
            if(args[1].charAt(1)!='u') {
                System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
                return false;
            }
        } catch (Exception e) {
            System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
            return false;
        }

//        Password exist check
        try{
            password = args[2].substring(4, args[2].length() - 1);
            if(args[2].charAt(1)!='p') {
                System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
                return false;
            }
        }catch (Exception e){
            System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
            return false;
        }
        User question_user = new User(name,password);

        //FileUsers not
        File fileUsers = new File("./src/main/java/com/example/project/Users.csv");
        if(fileUsers.exists() == false) {
            System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
            return false;
        }

        //Username can't be found
        try (BufferedReader br = new BufferedReader(new FileReader(("./src/main/java/com/example/project/Users.csv")))) {
            String line;
            int founduser=0;
            while ((line = br.readLine()) != null) {
                User file_user= new User(line.split(",")[0], line.split(",")[1]);
                if(question_user.equals(file_user)){
                    founduser=1;
                }
            }
            if(founduser == 0){
                System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
                return false;
            }
        } catch (IOException e) {
            System.out.println("Read user not work");
        }

        return true;
    }

    static boolean checkSubmitQuizz(final String[] args){

        String name;
        String password;

//        Username exist check
        try {
            name = args[1].substring(4, args[1].length() - 1);
            if(args[1].charAt(1)!='u') {
                System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
                return false;
            }
        } catch (Exception e) {
            System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
            return false;
        }

//        Password exist check
        try{
            password = args[2].substring(4, args[2].length() - 1);
            if(args[2].charAt(1)!='p') {
                System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
                return false;
            }
        }catch (Exception e){
            System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
            return false;
        }
        User question_user = new User(name,password);

        //FileUsers not
        File fileUsers = new File("./src/main/java/com/example/project/Users.csv");
        if(fileUsers.exists() == false) {
            System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
            return false;
        }

        //Username can't be found
        try (BufferedReader br = new BufferedReader(new FileReader(("./src/main/java/com/example/project/Users.csv")))) {
            String line;
            int founduser=0;
            while ((line = br.readLine()) != null) {
                User file_user= new User(line.split(",")[0], line.split(",")[1]);
                if(question_user.equals(file_user)){
                    founduser=1;
                }
            }
            if(founduser == 0){
                System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
                return false;
            }
        } catch (IOException e) {
            System.out.println("Read user not work");
        }

//        Quizz id doesn't exist
        String idQuizz;
        try {
            idQuizz = args[3].substring(10, args[3].length() - 1);
        }catch (Exception e){
            System.out.println("{ 'status' : 'error', 'message' : 'No quizz identifier was provided'}");
            return false;
        }

//         Quizz id doesn't find
        int found = 0;
        File f = new File("./src/main/java/com/example/project/Quizz.csv");
        try (BufferedReader br = new BufferedReader(new FileReader(("./src/main/java/com/example/project/Quizz.csv")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] file_quizz;
                file_quizz = line.split(",");
                if(idQuizz.equals(file_quizz[0])) {
                    found = 1;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println();
        }

        if(found == 0) {
            System.out.println("{ 'status' : 'error', 'message' : 'No quiz was found'}");
            return false;
        }


        return true;
    }

    static boolean checkDeleteQuizz(final String[] args){

        String name;
        String password;

//        Username exist check
        try {
            name = args[1].substring(4, args[1].length() - 1);
            if(args[1].charAt(1)!='u') {
                System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
                return false;
            }
        } catch (Exception e) {
            System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
            return false;
        }

//        Password exist check
        try{
            password = args[2].substring(4, args[2].length() - 1);
            if(args[2].charAt(1)!='p') {
                System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
                return false;
            }
        }catch (Exception e){
            System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
            return false;
        }
        User question_user = new User(name,password);

//        FileUsers not
        File fileUsers = new File("./src/main/java/com/example/project/Users.csv");
        if(fileUsers.exists() == false) {
            System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
            return false;
        }

//        Username can't be found
        try (BufferedReader br = new BufferedReader(new FileReader(("./src/main/java/com/example/project/Users.csv")))) {
            String line;
            int founduser=0;
            while ((line = br.readLine()) != null) {
                User file_user= new User(line.split(",")[0], line.split(",")[1]);
                if(question_user.equals(file_user)){
                    founduser=1;
                }
            }
            if(founduser == 0){
                System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
                return false;
            }
        } catch (IOException e) {
            System.out.println("Read user not work");
        }

//        Quizz id doesn't exist
        String idQuizz;

        try {
            idQuizz = args[3].substring(5, args[3].length() - 1);
        }catch (Exception e){
            System.out.println("{ 'status' : 'error', 'message' : 'No quizz identifier was provided'}");
            return false;
        }

//        Quizz id doesn't find
        int found = 0;
        File f = new File("./src/main/java/com/example/project/Quizz.csv");
        try (BufferedReader br = new BufferedReader(new FileReader(("./src/main/java/com/example/project/Quizz.csv")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] file_quizz;
                file_quizz = line.split(",");
                if(idQuizz.equals(file_quizz[0])) {
                    found = 1;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println();
        }

        if(found == 0) {
            System.out.println("{ 'status' : 'error', 'message' : 'No quiz was found'}");
            return false;
        }

        return true;
    }

    static boolean checkGetMySolutions(final String[] args){

        String name;
        String password;

//        Username exist check
        try {
            name = args[1].substring(4, args[1].length() - 1);
            if(args[1].charAt(1)!='u') {
                System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
                return false;
            }
        } catch (Exception e) {
            System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
            return false;
        }

//        Password exist check
        try{
            password = args[2].substring(4, args[2].length() - 1);
            if(args[2].charAt(1)!='p') {
                System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
                return false;
            }
        }catch (Exception e){
            System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
            return false;
        }
        User question_user = new User(name,password);

        //FileUsers not
        File fileUsers = new File("./src/main/java/com/example/project/Users.csv");
        if(fileUsers.exists() == false) {
            System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
            return false;
        }

        //Username can't be found
        try (BufferedReader br = new BufferedReader(new FileReader(("./src/main/java/com/example/project/Users.csv")))) {
            String line;
            int founduser=0;
            while ((line = br.readLine()) != null) {
                User file_user= new User(line.split(",")[0], line.split(",")[1]);
                if(question_user.equals(file_user)){
                    founduser=1;
                }
            }
            if(founduser == 0){
                System.out.println("{ 'status' : 'error', 'message' : 'Login failed'}");
                return false;
            }
        } catch (IOException e) {
            System.out.println("Read user not work");
        }

        return true;
    }
}
