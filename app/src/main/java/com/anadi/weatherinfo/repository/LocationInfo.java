package com.anadi.weatherinfo.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.anadi.weatherinfo.repository.data.WeatherInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class LocationInfo implements Serializable {
    private static int count = 0;
    private int id;
    private String cityName;
    private Country country;
    private WeatherInfo info;

    public LocationInfo(String cityName, Country country) {
        this.cityName = cityName;
        this.country = country;
        id = count++;
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

    private void writeObject(ObjectOutputStream oos)
            throws IOException {
        oos.defaultWriteObject();
    }

    private void readObject(ObjectInputStream ois)
            throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        id = count++;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof LocationInfo))
            return false;

        LocationInfo other = (LocationInfo)obj;

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
