package com.babat.deniz.brochat;

/**
 * Created by deniz on 12.12.2017.
 */

public class Profil {
    private String sender = new String();
    private String reciever = new String();
    private ChatMessage cm = new ChatMessage();

    public Profil(){

    }

    public Profil(String sender, String reviever, ChatMessage cm) {
        this.sender = sender;
        this.reciever = reviever;
        this.cm = cm;
    }


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public ChatMessage getCm() {
        return cm;
    }

    public void setCm(ChatMessage cm) {
        this.cm = cm;
    }

}
