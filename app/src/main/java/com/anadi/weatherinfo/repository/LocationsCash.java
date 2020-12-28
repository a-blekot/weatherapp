package com.anadi.weatherinfo.repository;

import android.content.Context;

import androidx.annotation.Nullable;

import com.anadi.weatherinfo.addlocation.AddLocationContract;
import com.anadi.weatherinfo.addlocation.LocationsProvider;
import com.anadi.weatherinfo.details.DetailsContract;
import com.anadi.weatherinfo.mainactivity.MainActivityContract;
import com.anadi.weatherinfo.repository.data.WeatherInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;

import timber.log.Timber;

public class LocationsCash extends Observable implements AddLocationContract.Model, MainActivityContract.Model,
        DetailsContract.Model {

    private static final String APP_DATA_FILE = "weatherinfo.db";
    private static final String APP_DATA_DIR = "db";
    private static final int TIME_TO_UPDATE = 1000 * 60 * 60; // data is considered to be "fresh" during one hour
    // Singleton with double check
    private static volatile LocationsCash instance;
    private final LocationsProvider locationsProvider = Locations.getInstance();
    private final InfoLoader infoLoader = InfoLoader.getInstance();
    private ArrayList<LocationInfo> locations = new ArrayList<>();

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
    public void loadLocations(Context context) {
        locationsProvider.loadLocations(context);
    }

    @Override
    public void saveData(Context context) {
        File path = new File(context.getFilesDir(), APP_DATA_DIR);
        if (!path.exists()) {
            if (!path.mkdirs()) {
                Timber.d("Failed to create app db dir!");
                return;
            }
        }

        File file = new File(path, APP_DATA_FILE);
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    Timber.d("Failed to create app db file!");
                    return;
                }
            } catch (IOException e) {
                Timber.d("Failed to create app db file!");
                e.printStackTrace();
                return;
            }
        }

        try (FileOutputStream fos = new FileOutputStream(file); ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(locations);
            oos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadData(Context context) {
        File path = new File(context.getFilesDir(), APP_DATA_DIR);
        if (!path.exists()) {
            Timber.d("No db dir!");
            return;
        }

        File file = new File(path, APP_DATA_FILE);
        if (!file.exists()) {
            Timber.d("No db file!");
            return;
        }

        try (FileInputStream fis = new FileInputStream(file); ObjectInputStream ois = new ObjectInputStream(fis)) {

            locations = (ArrayList<LocationInfo>) ois.readObject();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<LocationInfo> getLocations() {
        return locations;
    }

    @Override
    public boolean deleteLocation(LocationInfo locationInfo) {
        boolean result = locations.remove(locationInfo);
        if (result) {
            setChanged();
            notifyObservers();
        }

        return result;
    }

    @Override
    public @Nullable
    WeatherInfo getInfo(int id) {

        for (LocationInfo locationInfo : locations) {
            if (locationInfo.getId() == id) {
                return locationInfo.getInfo();
            }
        }

        return null;
    }

    @Override
    public boolean update(int id) {
        for (LocationInfo locationInfo : locations) {
            if (locationInfo.getId() == id) {

                WeatherInfo weatherInfo = infoLoader.load(locationInfo.getCityName(), locationInfo.getCountry());
                if (weatherInfo == null) {
                    Timber.d("No info loaded for location: " + locationInfo);
                    return false;
                }

                locationInfo.setInfo(weatherInfo);
                setChanged();
                notifyObservers(locationInfo);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean needUpdate(int id) {
        for (LocationInfo locationInfo : locations) {
            if (locationInfo.getId() == id) {
                return dataIsOld(locationInfo.getInfo().dt);
            }
        }

        Timber.d("There is no data at all for id = %d", id);
        return false;
    }

    private boolean load(final String cityName, final Country country) {

        WeatherInfo weatherInfo = infoLoader.load(cityName, country);

        if (weatherInfo == null) {
            Timber.d("No info loaded for location: %s, %s", cityName, country);
            return false;
        }

        final LocationInfo locationInfo = new LocationInfo(cityName, country);
        locationInfo.setInfo(weatherInfo);
        locations.add(locationInfo);
        setChanged();
        notifyObservers();

        return true;
    }

    private @Nullable
    LocationInfo getCashedInfo(final String cityName, final Country country) {

        for (LocationInfo locationInfo : locations) {
            if (locationInfo.getCityName().equals(cityName) && locationInfo.getCountry().equals(country)) {
                return locationInfo;
            }
        }

        return null;
    }

    private boolean dataIsOld(int timestamp) {
        return System.currentTimeMillis() - timestamp > TIME_TO_UPDATE;
    }
}