package org.example;

import java.util.Date;
import java.util.Stack;

public class Student extends User{

//    Arguments
    private String school;

//    Builders
    Student(){super();}
    Student(String name,String school){
        super(name);
        this.school = school;

    }

//    Methods

//    This is a method use to:
//    - throw CustomException if this user does actions for which he doesn't have permissions (1)
//    - set a specific text for this type of request (2)
//    - add request into user's request list(TreeSet) and the specific office's request list(TreeSet)
    @Override
    public void createRequest(Request request) throws CustomExeption {
//  (1)
        if(request.getType() == Request.requestsType.registration_salary_income){
            throw new CustomExeption("Utilizatorul de tip elev nu poate inainta o cerere de tip inregistrare venit salarial");
        }
        if(request.getType() == Request.requestsType.replacement_of_a_driver_license){
            throw new CustomExeption("Utilizatorul de tip elev nu poate inainta o cerere de tip inlocuire carnet de sofer");
        }
        if(request.getType() == Request.requestsType.creation_of_articles_of_incorporation){
            throw new CustomExeption("Utilizatorul de tip elev nu poate inainta o cerere de tip creare act constitutiv");
        }
        if(request.getType() == Request.requestsType.renewal_of_the_authorization){
            throw new CustomExeption("Utilizatorul de tip elev nu poate inainta o cerere de tip reinnoire autorizatie");
        }
        if(request.getType() == Request.requestsType.registration_of_pension_coupons){
            throw new CustomExeption("Utilizatorul de tip elev nu poate inainta o cerere de tip inregistrare cupoane de pensie");
        }
//  (2)
        request.setText("Subsemnatul " + name + ", elev la scoala " + school + ", va rog sa-mi aprobati urmatoarea solicitare: " + request.getType().name);
//  (3)
        requests_pending.add(request);
        ManagementPrimarie.officeStudent.requests_priority.add(request);

    }

//    This is a method use to:
//    - delete a request from pending list (1)
//    - delete a request from this user's specific office (2)
    @Override
    public void deleteRequest(Date date) {
//  (1)
        for(Request request : requests_pending)
            if(request.getDate().equals(date)) {
                requests_pending.remove(request);
                break;
            }
//  (2)
        for(Request request : ManagementPrimarie.officeStudent.requests_priority)
            if(request.getDate().equals(date)) {
                ManagementPrimarie.officeStudent.requests_priority.remove(request);
                break;
            }
    }

}
