package com.example.bicycle.models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "bike_table")
public class Bike {

    @SerializedName("bike_id")
    @Expose
    @PrimaryKey(autoGenerate = true)
    public int id;
    @SerializedName("model")
    @Expose
    @ColumnInfo(name = "model")
    public String model;
    @SerializedName("type")
    @Expose
    @ColumnInfo(name = "type")
    public String  type;
    @SerializedName("price")
    @Expose
    @ColumnInfo(name = "price")
    public String price;
    @SerializedName("image")
    @Expose
    @ColumnInfo(name = "image")
    public String image;
    @SerializedName("shop_id")
    @Expose
    @ColumnInfo(name = "shop_id")
    public int shop;

    @Ignore
    public Bike(){
    }

    @Ignore
    public Bike( String model, String image) {
        this.model = model;
        this.image = image;
    }


    public Bike( String model, String type, String price, String image) {
        this.model = model;
        this.type = type;
        this.price = price;
        this.image = image;
    }

    @Ignore
    public Bike(int id, String model, String price, String type) {
        this.id = id;
        this.model = model;
        this.price = price;
        this.type = type;
    }

    @Ignore
    public Bike(int id, String model, String type, String price,  String image) {
        this.id = id;
        this.model = model;
        this.type = type;
        this.price = price;
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getShop() {
        return shop;
    }

    public void setShop(int shop) {
        this.shop = shop;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getModel() {
        return model;
    }

    public String getType() {
        return type;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Bike{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", type='" + type + '\'' +
                ", price='" + price + '\'' +
                ", image='" + image + '\'' +
                ", shop='" + shop + '\'' +
                '}';
    }


}
