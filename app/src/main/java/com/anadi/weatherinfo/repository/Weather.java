package com.anadi.weatherinfo.repository;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather {

    // Weather condition id
    @SerializedName("id")
    @Expose
    public int id;

    // Group of weather parameters (Rain, Snow, Extreme etc.)
    @SerializedName("main")
    @Expose
    public String main;

    // Weather condition within the group. You can get the output in your language. Learn more
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("icon")
    @Expose
    public String icon;

}
