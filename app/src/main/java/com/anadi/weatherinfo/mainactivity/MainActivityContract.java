package com.anadi.weatherinfo.mainactivity;

import android.content.Context;

import com.anadi.weatherinfo.repository.LocationInfo;

import java.util.ArrayList;
import java.util.Observer;

public interface MainActivityContract {

    interface Presenter extends Observer {
        void loadLocations(Context context);
        void saveData(Context context);
        void loadData(Context context);

        ArrayList<LocationInfo> getLocations();

        boolean deleteLocation(LocationInfo locationInfo);
    }

    interface Model {
        void loadLocations(Context context);
        void saveData(Context context);
        void loadData(Context context);

        ArrayList<LocationInfo> getLocations();

        boolean deleteLocation(LocationInfo locationInfo);
    }
}
