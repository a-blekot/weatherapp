package com.anadi.weatherinfo.repository;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clouds {
    // Cloudiness, %
    @SerializedName("all")
    @Expose
    public int all;
}