package com.example.bicycle.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Participants {

    @SerializedName("pevent_id")
    @Expose
    public int participant_id;

    @SerializedName("puser_id")
    @Expose
    public int participant_user_id;


    @SerializedName("user_id")
    @Expose
    private int user_id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("phone")
    @Expose
    private String phone;


    public  Participants(){

    }

    public Participants(int participant_id, int participant_user_id, int user_id, String name, String lastname, String email, String password, String phone) {
        this.participant_id = participant_id;
        this.participant_user_id = participant_user_id;
        this.user_id = user_id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public int getParticipant_id() {
        return participant_id;
    }

    public void setParticipant_id(int participant_id) {
        this.participant_id = participant_id;
    }

    public int getParticipant_user_id() {
        return participant_user_id;
    }

    public void setParticipant_user_id(int participant_user_id) {
        this.participant_user_id = participant_user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Participants{" +
                "participant_id=" + participant_id +
                ", participant_user_id=" + participant_user_id +
                ", user_id=" + user_id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }






}
