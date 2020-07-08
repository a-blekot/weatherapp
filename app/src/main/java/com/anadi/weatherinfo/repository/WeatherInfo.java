package com.anadi.weatherinfo.repository;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherInfo implements Serializable {

    @SerializedName("coord")
    @Expose
    public Coord coord;

    // (more info Weather condition codes)
    @SerializedName("weather")
    @Expose
    public List<Weather> weather = null;

    // Internal parameter
    @SerializedName("base")
    @Expose
    public String base;
    @SerializedName("main")
    @Expose
    public Main main;
    @SerializedName("visibility")
    @Expose
    public int visibility;
    @SerializedName("wind")
    @Expose
    public Wind wind;
    @SerializedName("clouds")
    @Expose
    public Clouds clouds;
    @SerializedName("dt")
    @Expose

    // Time of data calculation, unix, UTC
    public int dt;
    @SerializedName("sys")
    @Expose


    public Sys sys;
    @SerializedName("timezone")
    @Expose

    // Shift in seconds from UTC
    // Devide in 3600 to get +/- hours
    public int timezone;

    // City ID
    @SerializedName("id")
    @Expose
    public int id;

    // City Name
    @SerializedName("name")
    @Expose
    public String name;

    // Internal parameter
    @SerializedName("cod")
    @Expose
    public int cod;

    public enum State {
        NONE(0, "None", "None", -1),
        CLEAR_SKY(800, "Clear", "Clear sky", 0),
        CLOUDS(801, "Clouds", "few clouds: 11-25%", 1);

        private int id;
        private String mainDescription;
        private String detailedDescription;
        private int iconId;

        State(int id, String mainDescription, String detailedDescription, int iconId) {
            this.id = id;
            this.mainDescription = mainDescription;
            this.detailedDescription = detailedDescription;
            this.iconId = iconId;
        }

        public int getId() {
            return id;
        }

        public String getMainDescription() {
            return mainDescription;
        }

        public String getDetailedDescription() {
            return detailedDescription;
        }

        public int getIconId() {
            return iconId;
        }
    }
}