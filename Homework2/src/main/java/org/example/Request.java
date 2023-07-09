package org.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class Request {
//  Arguments
    private String text;
    private Date date;
    static final SimpleDateFormat formatDate = new SimpleDateFormat("d-MMM-yyyy HH:mm:ss");
    private Integer priority;
    private requestsType type;
    private User user;

    enum requestsType {
        replacement_of_identity_card("inlocuire buletin"),
        registration_salary_income("inregistrare venit salarial"),
        replacement_of_a_driver_license("inlocuire carnet de sofer"),
        replacement_of_the_student_card("inlocuire carnet de elev"),
        creation_of_articles_of_incorporation("creare act constitutiv"),
        renewal_of_the_authorization("reinnoire autorizatie"),
        registration_of_pension_coupons("inregistrare cupoane de pensie");
        final String name;

        requestsType(String name) {
            this.name = name;
        }
//        There is an enum method which receive an enum type (String) and check each types to find the right one
        public static requestsType getRequestType(String type) {
            if (type.equals("inlocuire buletin")) {
                return requestsType.valueOf("replacement_of_identity_card");
            }
            if (type.equals("inregistrare venit salarial")) {
                return requestsType.valueOf("registration_salary_income");
            }
            if (type.equals("inlocuire carnet de sofer")) {
                return requestsType.valueOf("replacement_of_a_driver_license");
            }
            if (type.equals("inlocuire carnet de elev")) {
                return requestsType.valueOf("replacement_of_the_student_card");
            }
            if (type.equals("creare act constitutiv")) {
                return requestsType.valueOf("creation_of_articles_of_incorporation");
            }
            if (type.equals("reinnoire autorizatie")) {
                return requestsType.valueOf("renewal_of_the_authorization");
            }
            if (type.equals("inregistrare cupoane de pensie")) {
                return requestsType.valueOf("registration_of_pension_coupons");
            }
            return null;
        }
    }

//  Builders
    Request(String type, String requestTimePattern, String priority, User user) throws ParseException {
        this.type = requestsType.getRequestType(type);
        this.date = formatDate.parse(requestTimePattern);
        this.priority = Integer.valueOf(priority);
        this.user = user;
    }

//  Methods
    public requestsType getType() {
        return type;
    }
    public Date getDate() {
        return date;
    }
    public String getFormatDate() {
        return formatDate.format(date);
    }
    public Integer getPriority() {
        return priority;
    }

    public User getUser() {
        return user;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return formatDate.format(date) + " - " + text;
    }

//  Class

//    There are two classes use to:
//    - sort user's lists by date requests (1)
//    - sort office's list by priority and then by date requests (2)

//  (1)
    static class timeComparator implements Comparator<Request> {

        public int compare(Request s1, Request s2) {
            return s1.date.compareTo(s2.date);
        }

    }
//  (2)
    static class priorityComparator implements Comparator<Request> {

        public int compare(Request s1, Request s2) {
            if(s1.priority < s2.priority){return 1;}
            if(s1.priority > s2.priority){return -1;}
                return s1.date.compareTo(s2.date);
        }

    }

}