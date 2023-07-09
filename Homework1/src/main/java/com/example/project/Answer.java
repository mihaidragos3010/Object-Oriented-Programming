package com.example.project;

public class Answer {
    //Arguments
    private String text;
    private boolean value;
    private static int nrAnswer = 0;
    private int ID;

    //Builders
    Answer(String text,int value){
        this.text=text;
        if(value == 1)
            this.value = true;
        else
            this.value = false;
        nrAnswer++;
        ID=nrAnswer;
    }

    //Methods
    public String getText() {
        return text;
    }

    public boolean getValue(){
        return value;
    }
    public int getID(){return ID;}

    public static void setNrAnswer(int value){nrAnswer=nrAnswer-value;}

    @Override
    public String toString() {
        return text + " " + value;
    }

    @Override
    public boolean equals(Object obj) {
//        This method set if two answers are equals compare each text
        Answer x = (Answer) obj;
        if (this.text.equals(x.text))
            return true;
        return false;
    }

    public static void resetAnswersID(){nrAnswer = 0;}
}
