package com.anadi.weatherinfo.repository.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Sys implements Serializable {

    // Internal parameter
    @SerializedName("type")
    @Expose
    public int type;

    // Internal parameter
    @SerializedName("id")
    @Expose
    public int id;

    // Country code (GB, JP etc.)
    @SerializedName("country")
    @Expose
    public String country;

    // Sunrise time, unix, UTC
    @SerializedName("sunrise")
    @Expose
    public int sunrise;

    // Sunset time, unix, UTC
    @SerializedName("sunset")
    @Expose
    public int sunset;

}