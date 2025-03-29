package com.example.bicycle.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("event_id")
    @Expose
    public int id;

    @SerializedName("event_title")
    @Expose
    public String title;

    @SerializedName("adresse_evt")
    @Expose
    public String adresse;

    @SerializedName("date_evt")
    @Expose
    public String date;

    @SerializedName("time_evt")
    @Expose
    public String time;

    @SerializedName("user")
    @Expose
    public int user;

    public int image;

    public Event() {
    }

    public Event(String adresse, String date, String time) {
        this.adresse = adresse;
        this.date = date;
        this.time = time;
    }

    public Event(int id, String title, String adresse, String date, String time) {
        this.id = id;
        this.title = title;
        this.adresse = adresse;
        this.date = date;
        this.time = time;
    }

    public Event(int id, String title, String adresse, String date, String time, int user) {
        this.id = id;
        this.title = title;
        this.adresse = adresse;
        this.date = date;
        this.time = time;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", adresse='" + adresse + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", user=" + user +
                ", image=" + image +
                '}';
    }
}
