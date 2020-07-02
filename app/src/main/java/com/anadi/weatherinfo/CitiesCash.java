package com.anadi.weatherinfo;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class CitiesCash {

    private static String API_KEY = "f9dee5683fdf51c7b611df7f57f26926";
    private static String LOCATION;
    private static String urlString;

    private static ArrayList<CityInfo> cities = new ArrayList<>();

    public static boolean add(String cityName, Country country) {
        return load(cityName, country);
    }

    public static boolean add(String cityName, String countryName) {
        Country country = Location.getCountryByName(countryName);
        if (country == null) {
            Log.d("anadi_city_cash", "There is no such country: " + countryName);
            return false;
        }

        return load(cityName, country);
    }

    public static ArrayList<CityInfo> cities() {
        return cities;
    }

    private static boolean load(final String cityName, final Country country) {

        final CityInfo cityInfo = new CityInfo(cityName, country);

        new Thread(new Runnable() {
            public void run() {
                try {
                    StringBuilder result = new StringBuilder();

                    Log.d("anadi_load_weather", "Trying to load weather info for: " + cityInfo);

                    LOCATION = cityInfo.getCityName() + "," + cityInfo.getCountry().code.toLowerCase();
                    urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + LOCATION + "&appid=" + API_KEY + "&units=metric";

                    Log.d("anadi_load_weather", urlString);

                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setConnectTimeout(1000); // millis

                    if (conn.getResponseCode() == 404)
                    {
                        Log.d("anadi_load_weather", "error code 404 for:" + urlString);
                        return;
                    }


                    BufferedReader rd;

                    try {
                        rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                        e.printStackTrace(System.err);
                        return;
                    }

                    String line;
                    while ((line = rd.readLine()) != null)
                        result.append(line);

                    rd.close();
//                    Log.d("anadi_load_weather", result.toString());

                    Map<String, Object> respMap = jsonToMap(result.toString());
                    Map<String, Object> mainMap = jsonToMap(respMap.get("main").toString());
                    Map<String, Object> windMap = jsonToMap(respMap.get("wind").toString());


                    Log.d("anadi_load_weather", "Current tempreture: " + mainMap.get("temp"));
//                    Log.d("anadi_load_weather", "Current humidity: " + mainMap.get("humidity"));
//                    Log.d("anadi_load_weather", "Wind speeds: " + windMap.get("speed"));
//                    Log.d("anadi_load_weather", "Wind angle: " + windMap.get("deg"));

                    int temperature = (int)Float.parseFloat(mainMap.get("temp").toString());
                    int winSpeed = (int)Float.parseFloat(windMap.get("speed").toString());

                    cityInfo.setInfo(new WeatherInfo(temperature, winSpeed, WeatherInfo.State.CLOUDS));

                    if (cityInfo.validInfo()) {
                        cities.add(cityInfo);
                    }

                } catch (IOException e) {
                    System.err.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        }).start();

        return true;
    }

    private static Map<String, Object> jsonToMap(String str) {
        return new Gson().fromJson(str, new TypeToken<HashMap<String, Object>>() {
        }.getType());
    }


}
