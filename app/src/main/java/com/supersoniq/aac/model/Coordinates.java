package com.supersoniq.aac.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coordinates {

    @SerializedName("x")
    @Expose
    private Float x;
    @SerializedName("y")
    @Expose
    private Float y;

    public Float getX() {
        return x;
    }

    public Float getY() {
        return y;
    }
}