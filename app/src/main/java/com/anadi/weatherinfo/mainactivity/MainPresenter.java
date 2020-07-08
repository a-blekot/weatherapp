package com.anadi.weatherinfo.mainactivity;

import android.content.Context;

import com.anadi.weatherinfo.CitiesCash;
import com.anadi.weatherinfo.CityInfo;

import java.util.*;

public class MainPresenter implements MainActivityContract.Presenter {

    private MainActivityContract.Model model;

    public MainPresenter(Context context) {
        model = CitiesCash.getInstance();
        model.setContext(context);
    }

    @Override
    public void loadLocations() {
        model.loadLocations();
    }

    @Override
    public void saveData() {
        model.saveData();
    }

    @Override
    public void loadData() {
        model.loadData();
    }

    @Override
    public ArrayList<CityInfo> getCities() {
        return model.getCities();
    }
}
