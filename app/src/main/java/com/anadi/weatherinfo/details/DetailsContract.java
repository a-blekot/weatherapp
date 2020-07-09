package com.anadi.weatherinfo.details;

import androidx.annotation.StringRes;

import com.anadi.weatherinfo.repository.data.WeatherInfo;

public interface DetailsContract {

    interface View {
        void loading();

        void onError(@StringRes int resId);

        void onUpdateSuccess();
    }

    interface Presenter {
        void update(int id);

        WeatherInfo getInfo(int id);
    }

    interface Model {
        WeatherInfo getInfo(int id);
    }
}
