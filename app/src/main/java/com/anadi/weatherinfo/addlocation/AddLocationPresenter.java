package com.anadi.weatherinfo.addlocation;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.anadi.weatherinfo.CitiesCash;
import com.anadi.weatherinfo.R;

import timber.log.Timber;

public class AddLocationPresenter {

    private AddLocationView view;

    private Handler handler = new Handler();

    void bind(AddLocationView view) {
        this.view = view;
    }

    public void addLocation(final String selectedCity, final String selectedCountry) {
        view.loading();

        if (TextUtils.isEmpty(selectedCity) || TextUtils.isEmpty(selectedCountry) ||
                selectedCity.equalsIgnoreCase("Select Item") ||
                selectedCountry.equalsIgnoreCase("Select Item")) {
            Timber.d( "selectedCity: " + selectedCity + "selectedCountry: " + selectedCountry);
             view.onError(R.string.on_error_select_city);
            return;
        }

        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5000);
//                    boolean result = CitiesCash.add(selectedCity, selectedCountry);
                    if (true) {
                        onCityAdded();
                    } else {
                        onError();
                    }

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void onCityAdded() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                view.onAddedSuccess();
            }
        });
    }

    protected void onError() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                view.onError(R.string.on_error_select_city);
            }
        });
    }

}
