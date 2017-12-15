package com.babat.deniz.brochat;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by deniz on 12.12.2017.
 */

public class Profil implements Parcelable{
    private String sender = new String();
    private String reciever = new String();
    private ChatMessage cm = new ChatMessage();

    public Profil(){

    }

    public Profil(String sender, String reciever)
    {
        this.sender = sender;
        this.reciever = reciever;
    }

    public Profil(String sender, String reciever, ChatMessage cm) {
        this.sender = sender;
        this.reciever = reciever;
        this.cm = cm;
    }


    protected Profil(Parcel in) {
        sender = in.readString();
        reciever = in.readString();
    }

    public static final Creator<Profil> CREATOR = new Creator<Profil>() {
        @Override
        public Profil createFromParcel(Parcel in) {
            return new Profil(in);
        }

        @Override
        public Profil[] newArray(int size) {
            return new Profil[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sender);
        dest.writeString(reciever);
    }
}
