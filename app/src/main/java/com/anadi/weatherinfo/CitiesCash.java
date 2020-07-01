package com.anadi.weatherinfo;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class CitiesCash {

    private static String API_KEY = "f9dee5683fdf51c7b611df7f57f26926";
    private static String LOCATION = "Kyiv,ua";
    private static String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + LOCATION + "&appid=" + API_KEY + "&units=metric";

    private static ArrayList<CityInfo> cities = new ArrayList<>();

    public static boolean add(String cityName, String countryName) {
        return load(cityName, countryName);
    }

    private static boolean load(final String cityName, final String countryName) {

        final CityInfo cityInfo = new CityInfo(cityName, countryName);

        new Thread(new Runnable() {
            public void run() {
                try {
                    StringBuilder result = new StringBuilder();
                    URL url = new URL(urlString);
                    URLConnection conn = url.openConnection();
                    conn.setConnectTimeout(1000); // millis

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
                    Log.d("anadi", result.toString());

                    Map<String, Object> respMap = jsonToMap(result.toString());
                    Map<String, Object> mainMap = jsonToMap(respMap.get("main").toString());
                    Map<String, Object> windMap = jsonToMap(respMap.get("wind").toString());

                    Log.d("anadi", "Current tempreture: " + mainMap.get("temp"));
                    Log.d("anadi", "Current humidity: " + mainMap.get("humidity"));
                    Log.d("anadi", "Wind speeds: " + windMap.get("speed"));
                    Log.d("anadi", "Wind angle: " + windMap.get("deg"));

                    int temperature = (int)Float.parseFloat(mainMap.get("temp").toString());
                    int winSpeed = (int)Float.parseFloat(windMap.get("speed").toString());

                    cityInfo.setInfo(new WeatherInfo(temperature, winSpeed, WeatherInfo.State.CLOUDS));
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        }).start();

        if (cityInfo.validInfo()) {
            cities.add(cityInfo);
            return true;
        }

        return false;
    }

    private static Map<String, Object> jsonToMap(String str) {
        return new Gson().fromJson(str, new TypeToken<HashMap<String, Object>>() {
        }.getType());
    }
}
