package org.example;

import java.util.Date;
import java.util.TreeSet;


public abstract class User {

//    Arguments
    protected String name;
    public TreeSet<Request> requests_pending;
    public TreeSet <Request> requests_resolved;


//    Builders
    User(){}
    User(String name){
        this.name = name;
        this.requests_pending = new TreeSet<Request>(new Request.timeComparator());
        this.requests_resolved = new TreeSet<Request>(new Request.timeComparator());
    }
//    Methods
    public abstract void createRequest(Request request) throws CustomExeption;
    public abstract void deleteRequest(Date date);

    public void showRequestPending(){
        for(Request request : requests_pending)
            ManagementPrimarie.println(request.toString());
    }

    public void showRequestResolved(){
        for(Request request : requests_resolved)
            ManagementPrimarie.println(request.toString());
    }

}
