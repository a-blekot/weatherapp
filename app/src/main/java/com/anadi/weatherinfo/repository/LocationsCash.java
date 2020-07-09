package com.anadi.weatherinfo.repository;

import android.content.Context;

import com.anadi.weatherinfo.addlocation.AddLocationContract;
import com.anadi.weatherinfo.addlocation.LocationsProvider;
import com.anadi.weatherinfo.mainactivity.MainActivityContract;
import com.anadi.weatherinfo.repository.data.WeatherInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import timber.log.Timber;

public class LocationsCash implements AddLocationContract.Model, MainActivityContract.Model {

  private static final String APP_DATA_FILE = "weatherinfo.db";
  private static final String APP_DATA_DIR = "db";

  private LocationsProvider locationsProvider = Locations.getInstance();
  private InfoLoader infoLoader = InfoLoader.getInstance();

  private ArrayList<LocationInfo> locations = new ArrayList<>();
  private Context mContext;

  // Singleton with double check
  private static volatile LocationsCash instance;
  private LocationsCash() {}
  public static LocationsCash getInstance() {
    LocationsCash result = instance;
    if (result == null) {
      synchronized (LocationsCash.class) {
        result = instance;
        if (result == null) {
          instance = result = new LocationsCash();
        }
      }
    }
    return result;
  }

  @Override
  public boolean add(String cityName, String countryName) {
    Country country = locationsProvider.getCountryByName(countryName);

    if (country == null) {
      Timber.d("There is no such country: %s", countryName);
      return false;
    }

    LocationInfo locationInfo = getCashedInfo(cityName, country);
    if (locationInfo != null) {
      Timber.d("City already loaded: %s", cityName);
      return true;
    }

    return load(cityName, country);
  }

  @Override
  public void setContext(Context context) {
    locationsProvider.setContext(context);
    mContext = context;
  }

  @Override
  public void loadLocations() {
    locationsProvider.loadLocations();
  }

  @Override
  public void saveData() {
    File path = new File(mContext.getFilesDir(), APP_DATA_DIR);
    if(!path.exists()){
      path.mkdirs();
    }

    File file = new File(path, APP_DATA_FILE);
    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (IOException e) {
        Timber.d("Failed to create app db file!");
        e.printStackTrace();
      }
    }


    try(FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);) {

      oos.writeObject(locations);
//      for (CityInfo cityInfo: locations) {
//        oos.writeObject(cityInfo);
//      }

      oos.flush();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void loadData() {
    File path = new File(mContext.getFilesDir(), APP_DATA_DIR);
    if (!path.exists()) {
      Timber.d("No db dir!");
      return;
    }

    File file = new File(path, APP_DATA_FILE);
    if (!file.exists()) {
      Timber.d("No db file!");
      return;
    }

    try(FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);) {

      locations = (ArrayList<LocationInfo>) ois.readObject();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Override
  public ArrayList<LocationInfo> getLocations() {
    return locations;
  }

  private boolean load(final String cityName, final Country country) {

    WeatherInfo weatherInfo = infoLoader.load(cityName, country);

    if (weatherInfo == null) {
      Timber.d("No info loaded for location: " + cityName + ", " + country);
      return false;
    }

    final LocationInfo locationInfo = new LocationInfo(cityName, country);
    locationInfo.setInfo(weatherInfo);
    locations.add(locationInfo);

    return true;
  }

  private LocationInfo getCashedInfo(final String cityName, final Country country) {

    for (LocationInfo locationInfo : locations) {
      if (locationInfo.getCityName().equals(cityName) &&
              locationInfo.getCountry().equals(country))
        return locationInfo;
    }

    return null;
  }
}