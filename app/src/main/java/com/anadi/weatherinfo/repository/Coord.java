package com.anadi.weatherinfo.repository;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coord {
    // City geo location, longitude
    @SerializedName("lon")
    @Expose
    public float lon;

    // City geo location, latitude
    @SerializedName("lat")
    @Expose
    public float lat;
}