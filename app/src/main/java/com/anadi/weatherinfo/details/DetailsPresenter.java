package com.anadi.weatherinfo.details;

import android.os.Handler;

import com.anadi.weatherinfo.R;
import com.anadi.weatherinfo.repository.LocationInfo;
import com.anadi.weatherinfo.repository.LocationsCash;
import com.anadi.weatherinfo.repository.data.WeatherInfo;

import java.util.Observable;

import timber.log.Timber;

public class DetailsPresenter implements DetailsContract.Presenter {

    private final DetailsContract.View view;
    private final DetailsContract.Model model;
    private final Handler handler = new Handler();
    private final int id;

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
                } else {
                    onError();
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }).start();
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
    public WeatherInfo getInfo(int id) {
        return model.getInfo(id);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof LocationInfo && ((LocationInfo) arg).getId() == id) {
            onUpdated();
        }
    }

    private void onUpdated() {
        handler.post(() -> view.onUpdateSuccess());
    }

    private void onError() {
        handler.post(() -> view.onError(R.string.on_error_update));
    }
}
