package com.babat.deniz.brochat;

/**
 * Created by deniz on 12.12.2017.
 */

public class Profil {
    private String User = new String();
    private String sender = new String();
    private String reviever = new String();
    private ChatMessage cm = new ChatMessage();

    public Profil(){}

    public Profil(String user, String sender, String reviever, ChatMessage cm) {
        User = user;
        this.sender = sender;
        this.reviever = reviever;
        this.cm = cm;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReviever() {
        return reviever;
    }

    public void setReviever(String reviever) {
        this.reviever = reviever;
    }

    public ChatMessage getCm() {
        return cm;
    }

    public void setCm(ChatMessage cm) {
        this.cm = cm;
    }

}
