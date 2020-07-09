package com.anadi.weatherinfo.mainactivity;

import android.content.Context;

import com.anadi.weatherinfo.repository.LocationInfo;

import java.util.ArrayList;

public interface MainActivityContract {

    interface Presenter {
        void loadLocations(Context context);
        void saveData(Context context);
        void loadData(Context context);

        ArrayList<LocationInfo> getLocations();
    }

    interface Model {
        void loadLocations(Context context);
        void saveData(Context context);
        void loadData(Context context);

        ArrayList<LocationInfo> getLocations();
    }
}
