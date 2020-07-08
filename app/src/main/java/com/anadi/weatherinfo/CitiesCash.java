package com.anadi.weatherinfo;

import android.content.Context;
import android.os.Environment;

import com.anadi.weatherinfo.addlocation.AddLocationContract;
import com.anadi.weatherinfo.addlocation.LocationsProvider;
import com.anadi.weatherinfo.mainactivity.MainActivityContract;
import com.anadi.weatherinfo.repository.WeatherInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import timber.log.Timber;

public class CitiesCash implements AddLocationContract.Model, MainActivityContract.Model {

  private static final String APP_DATA_FILE = "weatherinfo.db";
  private static final String APP_DATA_DIR = "db";

  private LocationsProvider locations = Locations.getInstance();
  private InfoLoader infoLoader = InfoLoader.getInstance();

  private ArrayList<CityInfo> cities = new ArrayList<>();
  private Context mContext;

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
      Timber.d("There is no such country: %s", countryName);
      return false;
    }

    CityInfo cityInfo = getCashedInfo(cityName, country);
    if (cityInfo != null) {
      Timber.d("City already loaded: %s", cityName);
      return true;
    }

    return load(cityName, country);
  }

  @Override
  public void setContext(Context context) {
    locations.setContext(context);
    mContext = context;
  }

  @Override
  public void loadLocations() {
    locations.loadLocations();
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

      oos.writeObject(cities);
//      for (CityInfo cityInfo: cities) {
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

      cities = (ArrayList<CityInfo>) ois.readObject();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Override
  public ArrayList<CityInfo> getCities() {
    return cities;
  }

  private boolean load(final String cityName, final Country country) {

    WeatherInfo weatherInfo = infoLoader.load(cityName, country);

    if (weatherInfo == null) {
      Timber.d("No info loaded for location: " + cityName + ", " + country);
      return false;
    }

    final CityInfo cityInfo = new CityInfo(cityName, country);
    cityInfo.setInfo(weatherInfo);
    cities.add(cityInfo);

    return true;
  }

  private CityInfo getCashedInfo(final String cityName, final Country country) {

    for (CityInfo cityInfo : cities) {
      if (cityInfo.getCityName().equals(cityName) &&
              cityInfo.getCountry().equals(country))
        return cityInfo;
    }

    return null;
  }
}