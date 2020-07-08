package com.anadi.weatherinfo.repository;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wind {

    // Wind speed.
    // Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour.
    @SerializedName("speed")
    @Expose
    public float speed;

    // Wind direction, degrees (meteorological)
    @SerializedName("deg")
    @Expose
    public int deg;

}