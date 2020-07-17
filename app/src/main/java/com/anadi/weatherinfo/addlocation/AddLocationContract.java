package com.anadi.weatherinfo.addlocation;

import androidx.annotation.StringRes;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Observer;

public interface AddLocationContract {

    interface View {

        void loading();

        void onError(@StringRes int resId);

        void onAddedSuccess();

        void updateCityList(@NotNull ArrayList<String> cities);
    }

    interface Presenter {
        void addLocation(final String cityName, final String countryName);

        void onCountrySelected(final String countryName);

        ArrayList<String> getCountryNames();
    }

    interface Model {
        boolean add(String cityName, String countryName);
    }
}
