package com.example.bicycle.models;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record {

    @SerializedName("record_id")
    @Expose
    private int id;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("daterecord")
    @Expose
    private String daterecord;
    @SerializedName("user_id")
    @Expose
    private String user_id;


    public Record() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        this.daterecord = formatter.format(calendar.getTime());
    }

    public Record(String distance, String address, String time) {
        this.distance = distance;
        this.address = address;
        this.time = time;
    }

    public Record(String distance, String address, String time,  String user_id) {
        this.distance = distance;
        this.address = address;
        this.time = time;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        this.daterecord =  formatter.format(calendar.getTime());
        this.user_id = user_id;
    }

    public Record(int id, String distance, String address, String time,  String user_id) {
        this.id = id;
        this.distance = distance;
        this.address = address;
        this.time = time;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        this.daterecord =  formatter.format(calendar.getTime());
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDaterecord() {
        return daterecord;
    }

    public void setDaterecord(String daterecord) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        this.daterecord = formatter.format(calendar.getTime());
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", distance='" + distance + '\'' +
                ", address='" + address + '\'' +
                ", time='" + time + '\'' +
                ", daterecord='" + daterecord + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }


}
