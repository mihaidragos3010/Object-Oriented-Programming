package org.example;

public class CustomExeption extends Exception{
    String message;
    CustomExeption(String message){
        this.message = message;
    }
}
