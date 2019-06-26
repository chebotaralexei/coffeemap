package com.supersoniq.aac.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoffeeShop {

    public CoffeeShop() {
    }

    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("coordinates")
    @Expose
    private Coordinates coordinates;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("shop")
    @Expose
    private String shop;
    @SerializedName("time")
    @Expose
    private String time;

    public String getAddress() {
        return address;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public String getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public String getShop() {
        return shop;
    }

    public String getTime() {
        return time;
    }
}
