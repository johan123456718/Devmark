package com.example.devmark.model;

/**
 * Models a chat
 */
public class Chat {

    private String message;
    private String reciever;
    private String sender;

    public Chat(){

    }

    public Chat(String message, String reciever, String sender){
        this.message = message;
        this.reciever = reciever;
        this.sender = sender;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
