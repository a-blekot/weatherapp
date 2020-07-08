package com.anadi.weatherinfo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import timber.log.Timber;

public class InfoLoader {

    // Singleton with double check
    private static volatile InfoLoader instance;
    private InfoLoader() {}
    public static InfoLoader getInstance() {
        InfoLoader result = instance;
        if (result == null) {
            synchronized (InfoLoader.class) {
                result = instance;
                if (result == null) {
                    instance = result = new InfoLoader();
                }
            }
        }
        return result;
    }

    private static String API_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static String API_KEY = "f9dee5683fdf51c7b611df7f57f26926";


    public WeatherInfo load(final String cityName, final Country country) {
        try {
            Timber.d("Trying to load weather info for: " + cityName + ", " + country);

            String urlString = composeUrl(cityName + "," + country.code.toLowerCase());
            Timber.d(urlString);

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(1000); // millis

            if (conn.getResponseCode() == 404) {
                Timber.d("error code 404 for:" + urlString);
                return null;
            }

            BufferedReader rd;

            try {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } catch (IOException e) {
                System.err.println(e.getMessage());
                e.printStackTrace(System.err);
                return null;
            }

            String line;
            StringBuilder result = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            rd.close();

            Map<String, Object> respMap = jsonToMap(result.toString());
            Map<String, Object> mainMap = jsonToMap(respMap.get("main").toString());
            Map<String, Object> windMap = jsonToMap(respMap.get("wind").toString());

            Timber.d("Current tempreture: " + mainMap.get("temp"));
            //                    Timber.d( "Current humidity: " + mainMap.get("humidity"));
            //                    Timber.d( "Wind speeds: " + windMap.get("speed"));
            //                    Timber.d( "Wind angle: " + windMap.get("deg"));

            int temperature = (int) Float.parseFloat(mainMap.get("temp").toString());
            int winSpeed = (int) Float.parseFloat(windMap.get("speed").toString());

            return new WeatherInfo(temperature, winSpeed, WeatherInfo.State.CLOUDS);
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private Map<String, Object> jsonToMap(String str) {
        return new Gson().fromJson(str, new TypeToken<HashMap<String, Object>>() {}.getType());
    }

    private String composeUrl(String location) {
        StringBuilder stringBuilder = new StringBuilder(130);
        stringBuilder.append(API_URL);
        stringBuilder.append(location);
        stringBuilder.append("&appid=");
        stringBuilder.append(API_KEY);
        stringBuilder.append("&units=metric");

        return stringBuilder.toString();
    }
}