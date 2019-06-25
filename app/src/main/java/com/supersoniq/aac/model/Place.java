package com.supersoniq.aac.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Place {

    public Place() {
    }

    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("coordinates")
    @Expose
    public Coordinates coordinates;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("note")
    @Expose
    public String note;
    @SerializedName("shop")
    @Expose
    public String shop;
    @SerializedName("time")
    @Expose
    public String time;

}
