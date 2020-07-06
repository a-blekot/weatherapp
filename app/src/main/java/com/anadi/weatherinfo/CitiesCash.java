package com.anadi.weatherinfo;

import android.content.Context;

import com.anadi.weatherinfo.addlocation.AddLocationContract;
import com.anadi.weatherinfo.addlocation.LocationsProvider;
import com.anadi.weatherinfo.mainactivity.MainActivityContract;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import timber.log.Timber;

public class CitiesCash implements AddLocationContract.Model, MainActivityContract.Model {

  private LocationsProvider locations;

  private static String API_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
  private static String API_KEY = "f9dee5683fdf51c7b611df7f57f26926";
  private static CitiesCash instance = new CitiesCash();

  private ArrayList<CityInfo> cities = new ArrayList<>();

  private CitiesCash() {
    locations = Locations.getInstatnce();
  }
  public static CitiesCash getInstance() { return instance; }

  public boolean add(String cityName, String countryName) {
    Country country = locations.getCountryByName(countryName);
    if (country == null) {
      Timber.d("There is no such country: " + countryName);
      return false;
    }

    return load(cityName, country);
  }

  @Override
  public ArrayList<CityInfo> getCities() {
    return cities;
  }

  public void setContext(Context context) {
    locations.setContext(context);
  }

  public void loadLocations() {
    locations.loadLocations();
  }

  private boolean load(final String cityName, final Country country) {

    final CityInfo cityInfo = new CityInfo(cityName, country);

    try {
      StringBuilder result = new StringBuilder();

      Timber.d("Trying to load weather info for: " + cityInfo);

      String urlString = composeUrl(cityInfo.getCityName() + "," + cityInfo.getCountry().code.toLowerCase());
      Timber.d(urlString);

      URL url = new URL(urlString);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setConnectTimeout(1000); // millis

      if (conn.getResponseCode() == 404) {
        Timber.d("error code 404 for:" + urlString);
        return false;
      }

      BufferedReader rd;

      try {
        rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      } catch (IOException e) {
        System.err.println(e.getMessage());
        e.printStackTrace(System.err);
        return false;
      }

      String line;
      while ((line = rd.readLine()) != null) result.append(line);

      rd.close();
      //                    Timber.d( result.toString());

      Map<String, Object> respMap = jsonToMap(result.toString());
      Map<String, Object> mainMap = jsonToMap(respMap.get("main").toString());
      Map<String, Object> windMap = jsonToMap(respMap.get("wind").toString());

      Timber.d("Current tempreture: " + mainMap.get("temp"));
      //                    Timber.d( "Current humidity: " + mainMap.get("humidity"));
      //                    Timber.d( "Wind speeds: " + windMap.get("speed"));
      //                    Timber.d( "Wind angle: " + windMap.get("deg"));

      int temperature = (int) Float.parseFloat(mainMap.get("temp").toString());
      int winSpeed = (int) Float.parseFloat(windMap.get("speed").toString());

      cityInfo.setInfo(new WeatherInfo(temperature, winSpeed, WeatherInfo.State.CLOUDS));

      if (cityInfo.validInfo()) {
        cities.add(cityInfo);
      }

    } catch (IOException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
      return false;
    }

    return true;
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
