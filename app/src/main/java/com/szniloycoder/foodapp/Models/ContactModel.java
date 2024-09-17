package com.szniloycoder.foodapp.Models;

public class ContactModel {

    String answer;
    private boolean isExpanded = false;
    String question;

    public ContactModel(String question2, String answer2) {
        this.question = question2;
        this.answer = answer2;
    }

    public String getQuestion() {
        return this.question;
    }

    public String getAnswer() {
        return this.answer;
    }

    public boolean isExpanded() {
        return this.isExpanded;
    }

    public void setExpanded(boolean expanded) {
        this.isExpanded = expanded;
    }
}
