package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class OfficeClerk {

//    Arguments
    String name;

//    Builders
    OfficeClerk(String name){
        this.name = name;
    }

//    Methods

//    There is a generics method use to:
//    - get the priority request from specific office (1)
//    - remove this request from user's pending list (2)
//    - add this request into user's resolved list (3)
//    - create a file with this OfficeClerk's name and then write solved request into it (4)
    public <People extends User> void resolveRequest(People people){
        Request request= null;
//  (1)
        if(people instanceof Person){request = ManagementPrimarie.officePerson.getRequest();}
        if(people instanceof Employee){request = ManagementPrimarie.officeEmployee.getRequest();}
        if(people instanceof Pensioner){request = ManagementPrimarie.officePensioner.getRequest();}
        if(people instanceof Student){request = ManagementPrimarie.officeStudent.getRequest();}
        if(people instanceof JuridicalEntity){request = ManagementPrimarie.officeJuridicalEntity.getRequest();}
//  (2) (3)
        User userRequest = request.getUser();
        userRequest.requests_pending.remove(request);
        userRequest.requests_resolved.add(request);
//  (4)
        try (FileWriter fw = new FileWriter("src/main/resources/output/" + "functionar_" + name + ".txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            out.println(request.getFormatDate() + " - " + userRequest.name);

        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }

    }

}
