package com.anadi.weatherinfo.details;

import android.content.Context;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import com.anadi.weatherinfo.repository.data.WeatherInfo;

public interface DetailsContract {

    interface View {

        void loading();

        void onError(@StringRes int resId);

        void onUpdateSuccess(boolean needRedraw);
    }

    interface Presenter {
        void update(int id);

        WeatherInfo getInfo(int id);
    }

    interface Model {
        WeatherInfo getInfo(int id);
        boolean update(int id);
        boolean alreadyUpToDate(int id);
    }
}
