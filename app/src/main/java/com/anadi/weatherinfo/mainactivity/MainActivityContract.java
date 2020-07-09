package com.anadi.weatherinfo.mainactivity;

import android.content.Context;

import com.anadi.weatherinfo.repository.LocationInfo;

import java.util.ArrayList;

public interface MainActivityContract {

    interface Presenter {
        void loadLocations();
        void saveData();
        void loadData();

        ArrayList<LocationInfo> getLocations();
    }

    interface Model {
        void setContext(Context context);
        void loadLocations();
        void saveData();
        void loadData();

        ArrayList<LocationInfo> getLocations();
    }
}
