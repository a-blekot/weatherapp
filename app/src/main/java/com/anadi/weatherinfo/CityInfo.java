package com.anadi.weatherinfo;

import androidx.annotation.Nullable;

import java.util.*;

public class CityInfo {
    private String cityName;
    private String countryName;
    private WeatherInfo info;

    public CityInfo(String cityName, String countryName) {
        this.cityName = cityName;
        this.countryName = countryName;
        info = new WeatherInfo(0, 0, WeatherInfo.State.NONE);
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public WeatherInfo getInfo() {
        return info;
    }

    public void setInfo(WeatherInfo info) {
        this.info = info;
    }

    public boolean validInfo() {
        return info.getState() != WeatherInfo.State.NONE;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (null == obj)
            return false;

        if (!(obj instanceof CityInfo))
            return false;

        CityInfo other = (CityInfo)obj;

        return other.cityName.equals(cityName) &&
                other.countryName.equals(countryName) &&
                other.info.equals(info);
    }
}
