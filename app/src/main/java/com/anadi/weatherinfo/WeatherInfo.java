package com.anadi.weatherinfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.*;

public class WeatherInfo {
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

    private int temperature;
    private int windSpeed;
    private WeatherInfo.State state;

    public WeatherInfo(int temperature, int windSpeed, State state) {
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.state = state;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof WeatherInfo))
            return false;

        WeatherInfo other = (WeatherInfo)obj;

        return other.temperature == temperature &&
                other.windSpeed == windSpeed &&
                other.state.id == state.id;
    }
}
