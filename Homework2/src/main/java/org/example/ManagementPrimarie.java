package org.example;

import java.io.*;
import java.text.ParseException;
import java.util.*;


public class ManagementPrimarie {
    static String path;
    static BufferedReader reader;
    static Map<String,User> users = new HashMap<>();
    static Office<Person> officePerson = new Office();
    static Office<Employee> officeEmployee = new Office();
    static Office<Pensioner> officePensioner = new Office();
    static Office<Student> officeStudent = new Office();
    static Office<JuridicalEntity> officeJuridicalEntity = new Office();

//    This is a static methods use to read line by line from a file. It takes file's line and split it for returning an
//    array string with each word which was separated by ";". When file ended method catch NullPointerException and
//    close file reader and returning null
    public static String[] read() throws IOException {
        String[] line;
        if(reader == null) {
            try {
                reader = new BufferedReader(new FileReader("src/main/resources/input/" + path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            line = reader.readLine().split("; ");
        }catch (IOException e){
            reader.close();
            return null;
        }
        catch (NullPointerException e){
            reader.close();
            return null;
        }

        return line;
    }

//    This is a methods use to print (without end line) the text sent as a parameter in file
    public static void print(String text) {
        try (FileWriter fw = new FileWriter("src/main/resources/output/" + path, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            out.print(text);

        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }

//    This is a methods use to print (with end line) the text sent as a parameter in file
    public static void println(String text) {
        try (FileWriter fw = new FileWriter("src/main/resources/output/" + path, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            out.println(text);

        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }

//    This is a method used to reset all static arguments from ManagmentPrimarie for each other tests
    static void resetArguments(String args){
        path = args;
        reader = null;
        users = new HashMap<String,User>();
        officePerson = new Office();
        officeEmployee = new Office();
        officePensioner = new Office();
        officeStudent = new Office();
        officeJuridicalEntity = new Office();
    }

    public static void main(String[] args) throws IOException, ParseException {

        resetArguments(args[0]);

        String[] line = read();
        while( line != null){

            if(line[0].equals("adauga_utilizator")){
                User user = null;
                if(line[1].equals("persoana")) {user = new Person(line[2]);}
                if(line[1].equals("angajat")) {user = new Employee(line[2],line[3]);}
                if(line[1].equals("pensionar")) {user = new Pensioner(line[2]);}
                if(line[1].equals("elev")) {user = new Student(line[2],line[3]);}
                if(line[1].equals("entitate juridica")) {user = new JuridicalEntity(line[2],line[3]);}
                users.put(line[2],user);
            }

//            In this block I creat a request and throws CustomException if user tried to make non permission actions
            if(line[0].equals("cerere_noua")){
                String name = line[1];
                User user = users.get(name);
                Request request = new Request(line[2],line[3],line[4],user);
                try {
                    user.createRequest(request);
                }catch(CustomExeption e){
                    println(e.message);
                }
            }

            if(line[0].equals("retrage_cerere")){
                String name = line[1];
                Date date = Request.formatDate.parse(line[2]);
                users.get(name).deleteRequest(date);
            }

            if(line[0].equals("afiseaza_cereri_in_asteptare")){
                String name = line[1];
                println(name + " - cereri in asteptare:");
                users.get(name).showRequestPending();
            }

            if(line[0].equals("afiseaza_cereri_finalizate")){
                String name = line[1];
                println(name + " - cereri in finalizate:");
                users.get(name).showRequestResolved();
            }

            if(line[0].equals("afiseaza_cereri")){
                String officeName = line[1];
                if(officeName.equals("persoana")){officePerson.showRequests(new Person());}
                if(officeName.equals("angajat")){officeEmployee.showRequests(new Employee());}
                if(officeName.equals("pensionar")){officePensioner.showRequests(new Pensioner());}
                if(officeName.equals("elev")){officeStudent.showRequests(new Student());}
                if(officeName.equals("entitate juridica")){officeJuridicalEntity.showRequests(new JuridicalEntity());}
            }

//            In this block I create an OfficeClerk and add into he's specific office
            if(line[0].equals("adauga_functionar")){
                String officeName = line[1];
                OfficeClerk officeClerk = new OfficeClerk(line[2]);
                if(officeName.equals("persoana")){officePerson.office_clerks.put(line[2],officeClerk);}
                if(officeName.equals("angajat")){officeEmployee.office_clerks.put(line[2],officeClerk);}
                if(officeName.equals("pensionar")){officePensioner.office_clerks.put(line[2],officeClerk);}
                if(officeName.equals("elev")){officeStudent.office_clerks.put(line[2],officeClerk);}
                if(officeName.equals("entitate juridica")){officeJuridicalEntity.office_clerks.put(line[2],officeClerk);}
            }

            if(line[0].equals("rezolva_cerere")){
                String officeName = line[1];
                String clerkName = line[2];
                if(officeName.equals("persoana")){officePerson.office_clerks.get(clerkName).resolveRequest(new Person());}
                if(officeName.equals("angajat")){officeEmployee.office_clerks.get(clerkName).resolveRequest(new Employee());}
                if(officeName.equals("pensionar")){officePensioner.office_clerks.get(clerkName).resolveRequest(new Pensioner());}
                if(officeName.equals("elev")){officeStudent.office_clerks.get(clerkName).resolveRequest(new Student());}
                if(officeName.equals("entitate juridica")){officeJuridicalEntity.office_clerks.get(clerkName).resolveRequest(new JuridicalEntity());}
            }

            line = read();
        }

    }
}