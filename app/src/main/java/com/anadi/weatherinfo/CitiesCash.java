package com.anadi.weatherinfo;

import android.content.Context;

import com.anadi.weatherinfo.addlocation.AddLocationContract;
import com.anadi.weatherinfo.addlocation.LocationsProvider;
import com.anadi.weatherinfo.mainactivity.MainActivityContract;

import java.util.*;

import timber.log.Timber;

public class CitiesCash implements AddLocationContract.Model, MainActivityContract.Model {

  private LocationsProvider locations = Locations.getInstance();
  private InfoLoader infoLoader = InfoLoader.getInstance();

  private ArrayList<CityInfo> cities = new ArrayList<>();

  // Singleton with double check
  private static volatile CitiesCash instance;
  private CitiesCash() {}
  public static CitiesCash getInstance() {
    CitiesCash result = instance;
    if (result == null) {
      synchronized (CitiesCash.class) {
        result = instance;
        if (result == null) {
          instance = result = new CitiesCash();
        }
      }
    }
    return result;
  }

  @Override
  public boolean add(String cityName, String countryName) {
    Country country = locations.getCountryByName(countryName);

    if (country == null) {
      Timber.d("There is no such country: " + countryName);
      return false;
    }

    CityInfo cityInfo = getCashedInfo(cityName, country);
    if (cityInfo != null) {
      Timber.d("City already loaded: " + cityName);
      return true;
    }

    return load(cityName, country);
  }

  @Override
  public void setContext(Context context) {
    locations.setContext(context);
  }

  @Override
  public void loadLocations() {
    locations.loadLocations();
  }

  @Override
  public ArrayList<CityInfo> getCities() {
    return cities;
  }

  private boolean load(final String cityName, final Country country) {

    WeatherInfo weatherInfo = infoLoader.load(cityName, country);

    if (weatherInfo == null || !weatherInfo.validState()) {
      Timber.d("No info loaded for location: " + cityName + ", " + country);
      return false;
    }

    final CityInfo cityInfo = new CityInfo(cityName, country);
    cityInfo.setInfo(weatherInfo);
    cities.add(cityInfo);

    return true;
  }

  private CityInfo getCashedInfo(final String cityName, final Country country) {

    Iterator<CityInfo> it = cities.listIterator();
    while (it.hasNext()) {
      CityInfo cityInfo = it.next();
      if (cityInfo.getCityName().equals(cityName) &&
          cityInfo.getCountry().equals(country))
        return cityInfo;
    }

    return null;
  }
}