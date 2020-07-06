package com.anadi.weatherinfo.addlocation;

import android.os.Handler;
import android.text.TextUtils;

import com.anadi.weatherinfo.CitiesCash;
import com.anadi.weatherinfo.Locations;
import com.anadi.weatherinfo.R;

import java.util.ArrayList;

import timber.log.Timber;

public class AddLocationPresenter implements AddLocationContract.Presenter {

    private AddLocationContract.View view;
    private AddLocationContract.Model model;
    private LocationsProvider locations;
    private Handler handler = new Handler();

    AddLocationPresenter(AddLocationContract.View view) {
        this.view = view;
        model = CitiesCash.getInstance();
        locations = Locations.getInstatnce();
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

                    boolean result = model.add(selectedCity, selectedCountry);

                    if (result) {
                        onCityAdded();
                    }
                    else {
                        onError();
                    }

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public void onCountrySelected(String countryName) {
        view.updateCityList(locations.getCityNames(countryName));
    }

    @Override
    public ArrayList<String> getCountryNames() {
        return locations.getCountryNames();
    }

    private void onCityAdded() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                view.onAddedSuccess();
            }
        });
    }

    private void onError() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                view.onError(R.string.on_error_select_city);
            }
        });
    }
}
