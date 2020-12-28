package com.anadi.weatherinfo.repository.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Main implements Serializable {

    // Temperature.
    // Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
    @SerializedName("temp")
    @Expose
    public float temp;

    // Temperature. This temperature parameter accounts
    // for the human perception of weather.
    @SerializedName("feels_like")
    @Expose
    public float feelsLike;

    // Minimum temperature at the moment.
    // This is minimal currently observed
    // temperature (within large megalopolises and urban areas).
    @SerializedName("temp_min")
    @Expose
    public float tempMin;

    // Maximum temperature at the moment.
    @SerializedName("temp_max")
    @Expose
    public float tempMax;

    //    Atmospheric pressure (on the sea level,
    //    if there is no sea_level or grnd_level data), hPa
    @SerializedName("pressure")
    @Expose
    public int pressure;

    // Humidity, %
    @SerializedName("humidity")
    @Expose
    public int humidity;

}
