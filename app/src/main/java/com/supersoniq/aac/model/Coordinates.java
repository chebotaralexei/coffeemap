package com.supersoniq.aac.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coordinates {

    @SerializedName("x")
    @Expose
    public Float x;
    @SerializedName("y")
    @Expose
    public Float y;

}