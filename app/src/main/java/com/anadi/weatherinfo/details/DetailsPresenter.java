package com.anadi.weatherinfo.details;

import android.os.Handler;
import android.widget.Toast;

import com.anadi.weatherinfo.R;
import com.anadi.weatherinfo.repository.LocationsCash;
import com.anadi.weatherinfo.repository.data.WeatherInfo;

import java.util.Observable;

import timber.log.Timber;

public class DetailsPresenter implements DetailsContract.Presenter {

    private DetailsContract.View view;
    private DetailsContract.Model model;
    private Handler handler = new Handler();
    private int id;

    DetailsPresenter(DetailsContract.View view, int id) {
        this.view = view;
        model = LocationsCash.getInstance();
        this.id = id;

        onUpdated();
    }

    @Override
    public void update() {
        if (!model.needUpdate(id)) {
            Timber.d("No need for update. Data is fresh id = %d", id);
            return;
        }

        view.loading();

        new Thread(() -> {
            try {

                boolean result = model.update(id);

                if (result) {
                    onUpdated();
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

    @Override
    public void update(Observable o, Object arg) {
        onUpdated();
    }

    private void onUpdated() {
        handler.post(() -> view.onUpdateSuccess());
    }

    private void onError() {
        handler.post(() -> view.onError(R.string.on_error_update));
    }
}
