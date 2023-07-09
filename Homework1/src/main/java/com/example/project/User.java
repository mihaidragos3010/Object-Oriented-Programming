package com.example.project;

import java.io.*;

public class User {

    //Arguments
    private final String name;
    private final String password;

    //Builders
    public User(String name,String password){
        this.name=name;
        this.password=password;
    }

    //Methods

    public String getName(){return this.name;}
    public String getPassword(){return this.password;}
    public void createUser() {
//      Method create and write a user data in a file
        try (FileWriter fw = new FileWriter("./src/main/java/com/example/project/Users.csv", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            out.print(this.name);
            out.print(",");
            out.println(this.password);

            System.out.println("{'status':'ok','message':'User created successfully'}");

        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public void getMySolutions(){

//        Method step through solutions file line by line and show on the screen the required data. Index variable has
//        role to show data in a right format on the screen.
        int index = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(("./src/main/java/com/example/project/Solutions.csv")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] file_solutions;
                file_solutions = line.split(",");
                User file_user = new User(file_solutions[0],file_solutions[1]);     //read the name and password from the Solutions file
                if(this.equals(file_user)) {
                    index++;
                    if (index == 1)
                        System.out.print("{ 'status' : 'ok', 'message' : '[");
                    if (index > 1)
                        System.out.print(", ");
                    int score = (int)Float.parseFloat(file_solutions[4]) ;  //De vazut acasa cum faci sa afisezi 100.0 ca 100 !!!!!!
                    System.out.print("{\"quiz-id\" : \"" + file_solutions[2] + "\", \"quiz-name\" : \"" + file_solutions[3] + "\", \"score\" : \"" + score + "\", \"index_in_list\" : \"" + index + "\"}");
                }
            }
        } catch (Exception e) {}

        if(index != 0)
            System.out.println("]'}");
    }

    public boolean equals(User test_user) {
//        Method check if two users are the same comparing their name and password
        if(this.name.equals(test_user.name) && this.password.equals(test_user.password))
            return true;
        return false;
    }

    @Override
    public String toString() {
        return this.name + " " + this.password;
    }


}
