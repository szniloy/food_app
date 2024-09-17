package com.szniloycoder.foodapp.Models;

public class FAQ_itemModel {

    String answer;
    boolean isExpanded = false;
    String question;

    public FAQ_itemModel(String question2, String answer2) {
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
