package com.anadi.weatherinfo.details;

import android.os.Handler;

import androidx.annotation.DrawableRes;

import com.anadi.weatherinfo.R;
import com.anadi.weatherinfo.repository.LocationsCash;
import com.anadi.weatherinfo.repository.data.WeatherInfo;

public class DetailsPresenter implements DetailsContract.Presenter {

    DetailsContract.View view;
    DetailsContract.Model model;
    private Handler handler = new Handler();

    DetailsPresenter(DetailsContract.View view) {
        this.view = view;
        model = LocationsCash.getInstance();
    }

    @Override
    public void update(int id) {
        if (model.alreadyUpToDate(id)) {
            onUpdated(id, false);
            return;
        }

        view.loading();

        new Thread(() -> {
            try {

                boolean result = model.update(id);

                if (result) {
                    onUpdated(id, true);
                }
                else {
                    onError();
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public WeatherInfo getInfo(int id) {
        return model.getInfo(id);
    }

    private void onUpdated(int id, boolean needRedraw) {
        handler.post(() -> view.onUpdateSuccess(needRedraw));
    }

    private void onError() {
        handler.post(() -> view.onError(R.string.on_error_update));
    }
}
