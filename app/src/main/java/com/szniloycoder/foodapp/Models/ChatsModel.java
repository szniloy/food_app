package com.szniloycoder.foodapp.Models;

public class ChatsModel {

    private boolean isseen;
    private String key;
    private String message;
    private String receiver;
    private String sender;
    private Long time;

    public ChatsModel() {
    }

    public ChatsModel(String sender2, String receiver2, String message2, boolean isseen2, Long time2, String key2) {
        this.sender = sender2;
        this.receiver = receiver2;
        this.message = message2;
        this.isseen = isseen2;
        this.time = time2;
        this.key = key2;
    }

    public String getSender() {
        return this.sender;
    }

    public void setSender(String sender2) {
        this.sender = sender2;
    }

    public String getReceiver() {
        return this.receiver;
    }

    public void setReceiver(String receiver2) {
        this.receiver = receiver2;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message2) {
        this.message = message2;
    }

    public boolean isIsseen() {
        return this.isseen;
    }

    public void setIsseen(boolean isseen2) {
        this.isseen = isseen2;
    }

    public Long getTime() {
        return this.time;
    }

    public void setTime(Long time2) {
        this.time = time2;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key2) {
        this.key = key2;
    }
}
