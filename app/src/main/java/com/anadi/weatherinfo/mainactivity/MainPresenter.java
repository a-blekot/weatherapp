package com.anadi.weatherinfo.mainactivity;

import android.content.Context;

import com.anadi.weatherinfo.repository.LocationsCash;
import com.anadi.weatherinfo.repository.LocationInfo;

import java.util.*;

public class MainPresenter implements MainActivityContract.Presenter{

    private MainActivityContract.Model model;

    public MainPresenter(Context context) {
        model = LocationsCash.getInstance();
    }

    @Override
    public void loadLocations(Context context) {
        model.loadLocations(context);
    }

    @Override
    public void saveData(Context context) {
        model.saveData(context);
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public ArrayList<LocationInfo> getLocations() {
        return model.getLocations();
    }

    @Override
    public boolean deleteLocation(LocationInfo locationInfo) {
        return model.deleteLocation(locationInfo);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
