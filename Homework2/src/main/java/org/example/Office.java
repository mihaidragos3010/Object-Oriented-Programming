package org.example;

import java.util.*;

public class Office <People extends User>{

//    Arguments
    public TreeSet<Request> requests_priority;
    public Map<String,OfficeClerk> office_clerks;

//    Bulders
    Office(){
        requests_priority = new TreeSet<Request>(new Request.priorityComparator());
        office_clerks = new HashMap<String,OfficeClerk>();
    }

//    Methods

    public void showRequests (People people){
        if(people instanceof Person){ManagementPrimarie.println("persoana - cereri in birou:");}
        if(people instanceof Employee){ManagementPrimarie.println("angajat - cereri in birou:");}
        if(people instanceof Pensioner){ManagementPrimarie.println("pensionar - cereri in birou:");}
        if(people instanceof Student){ManagementPrimarie.println("elev - cereri in birou:");}
        if(people instanceof JuridicalEntity){ManagementPrimarie.println("entitate juridica - cereri in birou:");}
            
        for(Request request : requests_priority){
            ManagementPrimarie.print(request.getPriority() +" - ");
            ManagementPrimarie.println(request.toString());
        }
    }

//    There is a method use to get first request from list and remove it then return this request
    public Request getRequest(){
        Request request = requests_priority.first();
        requests_priority.remove(request);
        return request;
    }

}
