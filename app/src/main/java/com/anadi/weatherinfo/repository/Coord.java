package com.anadi.weatherinfo.repository;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Coord implements Serializable {
    // City geo location, longitude
    @SerializedName("lon")
    @Expose
    public float lon;

    // City geo location, latitude
    @SerializedName("lat")
    @Expose
    public float lat;
}