package com.anadi.weatherinfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.anadi.weatherinfo.repository.WeatherInfo;

import java.io.Serializable;

public class CityInfo implements Serializable {
    private static int count = 0;
    private int id = count++;
    private String cityName;
    private Country country;
    private WeatherInfo info;

    public CityInfo(String cityName, Country country) {
        this.cityName = cityName;
        this.country = country;
    }

    public int getId() { return id;}

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public WeatherInfo getInfo() {
        return info;
    }

    public void setInfo(WeatherInfo info) {
        this.info = info;
    }

    public boolean validInfo() {
        return info != null;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof CityInfo))
            return false;

        CityInfo other = (CityInfo)obj;

        return other.cityName.equals(cityName) &&
                other.country.equals(country) &&
                other.info.equals(info);
    }

    @NonNull
    @Override
    public String toString() {
        return cityName + " | " + country;
    }
}
