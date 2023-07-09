package org.example;

import java.util.Date;

public class Employee extends User{

//    Arguments
    private String company;
//    Builders
    Employee(){super();}
    Employee(String name, String company){
        super(name);
        this.company = company;
    }
//    Methods

//    This is a method use to:
//    - throw CustomException if this user does actions for which he doesn't have permissions (1)
//    - set a specific text for this type of request (2)
//    - add request into user's request list(TreeSet) and the specific office's request list(TreeSet) (3)
    @Override
    public void createRequest(Request request) throws CustomExeption{
//  (1)
        if(request.getType() == Request.requestsType.replacement_of_the_student_card){
            throw new CustomExeption("Utilizatorul de tip angajat nu poate inainta o cerere de tip inlocuire carnet de elev");
        }
        if(request.getType() == Request.requestsType.creation_of_articles_of_incorporation){
            throw new CustomExeption("Utilizatorul de tip angajat nu poate inainta o cerere de tip creare act constitutiv");
        }
        if(request.getType() == Request.requestsType.renewal_of_the_authorization){
            throw new CustomExeption("Utilizatorul de tip angajat nu poate inainta o cerere de tip reinnoire autorizatie");
        }
        if(request.getType() == Request.requestsType.registration_of_pension_coupons){
            throw new CustomExeption("Utilizatorul de tip angajat nu poate inainta o cerere de tip inregistrare cupoane de pensie");
        }
//  (2)
        request.setText("Subsemnatul "+ name +", angajat la compania " + company + ", va rog sa-mi aprobati urmatoarea solicitare: " + request.getType().name);
//  (3)
        requests_pending.add(request);
        ManagementPrimarie.officeEmployee.requests_priority.add(request);

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
        for(Request request : ManagementPrimarie.officeEmployee.requests_priority)
            if(request.getDate().equals(date)) {
                ManagementPrimarie.officeEmployee.requests_priority.remove(request);
                break;
            }
    }

}
