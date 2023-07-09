package org.example;

import java.util.Date;

public class JuridicalEntity extends User{

//    Arguments
    private String person;
//    Builders
    JuridicalEntity(){super();}
    JuridicalEntity(String name, String person){
        super(name);
        this.person = person;
}
//    Methods

//    This is a method use to:
//    - throw CustomException if this user does actions for which he doesn't have permissions (1)
//    - set a specific text for this type of request (2)
//    - add request into user's request list(TreeSet) and the specific office's request list(TreeSet) (3)
    @Override
    public void createRequest(Request request) throws CustomExeption {
//  (1)
        if(request.getType() == Request.requestsType.registration_salary_income){
            throw new CustomExeption("Utilizatorul de tip entitate juridica nu poate inainta o cerere de tip inregistrare venit salarial");
        }
        if(request.getType() == Request.requestsType.replacement_of_the_student_card){
            throw new CustomExeption("Utilizatorul de tip entitate juridica nu poate inainta o cerere de tip inlocuire carnet de elev");
        }
        if(request.getType() == Request.requestsType.replacement_of_identity_card){
            throw new CustomExeption("Utilizatorul de tip entitate juridica nu poate inainta o cerere de tip inlocuire buletin");
        }
        if(request.getType() == Request.requestsType.replacement_of_a_driver_license){
            throw new CustomExeption("Utilizatorul de tip entitate juridica nu poate inainta o cerere de tip inlocuire carnet de sofer");
        }
        if(request.getType() == Request.requestsType.registration_of_pension_coupons){
            throw new CustomExeption("Utilizatorul de tip entitate juridica nu poate inainta o cerere de tip inregistrare cupoane de pensie");
        }
//  (2)
        request.setText("Subsemnatul " + person + ", reprezentant legal al companiei " + name + ", va rog sa-mi aprobati urmatoarea solicitare: " + request.getType().name);
//  (3)
        requests_pending.add(request);
        ManagementPrimarie.officeJuridicalEntity.requests_priority.add(request);

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
        for(Request request : ManagementPrimarie.officeJuridicalEntity.requests_priority)
            if(request.getDate().equals(date)) {
                ManagementPrimarie.officeJuridicalEntity.requests_priority.remove(request);
                break;
            }
    }

}
