package com.anadi.weatherinfo.mainactivity;

import android.content.Context;

import com.anadi.weatherinfo.CityInfo;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface MainActivityContract {

    interface Presenter {
        void loadLocations();
        void saveData();
        void loadData();

        ArrayList<CityInfo> getCities();
    }

    interface Model {
        void setContext(Context context);
        void loadLocations();
        void saveData();
        void loadData();

        ArrayList<CityInfo> getCities();
    }
}
