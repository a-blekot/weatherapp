package com.anadi.weatherinfo.mainactivity;

import android.content.Context;
import android.os.Handler;

import com.anadi.weatherinfo.repository.LocationsCash;
import com.anadi.weatherinfo.repository.LocationInfo;

import java.util.*;

import timber.log.Timber;

public class MainPresenter implements MainActivityContract.Presenter{

    private MainActivityContract.Model model;
    private MainActivityContract.View view;
    private Handler handler = new Handler();

    public MainPresenter(MainActivityContract.View view) {
        model = LocationsCash.getInstance();
        this.view = view;
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
    public void subscribe() {
        Observable modelObservable = LocationsCash.getInstance();
        modelObservable.addObserver(this);
    }

    @Override
    public void unsubscribe() {
        Observable modelObservable = LocationsCash.getInstance();
        modelObservable.deleteObserver(this);
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
        Timber.d("1");
        handler.post(() -> view.onUpdate());
    }
}
