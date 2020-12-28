package com.anadi.weatherinfo.addlocation;

import android.os.Handler;
import android.text.TextUtils;

import com.anadi.weatherinfo.R;
import com.anadi.weatherinfo.repository.Locations;
import com.anadi.weatherinfo.repository.LocationsCash;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import timber.log.Timber;

public class AddLocationPresenter implements AddLocationContract.Presenter {

    private final AddLocationContract.View view;
    private final AddLocationContract.Model model;
    private final LocationsProvider locations;
    private final Handler handler = new Handler();

    private final Executor exec;

    AddLocationPresenter(AddLocationContract.View view) {
        this.view = view;
        model = LocationsCash.getInstance();
        locations = Locations.getInstance();
        exec = Executors.newSingleThreadExecutor();
    }

    public void addLocation(final String selectedCity, final String selectedCountry) {
        view.loading();

        if (TextUtils.isEmpty(selectedCity) || TextUtils.isEmpty(selectedCountry) || selectedCity.equalsIgnoreCase(
                "Select Item") || selectedCountry
                .equalsIgnoreCase("Select Item")) {
            Timber.d("selectedCity: " + selectedCity + "selectedCountry: " + selectedCountry);
            view.onError(R.string.on_error_select_city);
            return;
        }

        exec.execute(() -> {
            try {
                boolean result = model.add(selectedCity, selectedCountry);

                if (result) {
                    onCityAdded();
                } else {
                    onError();
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        });
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
        handler.post(() -> view.onAddedSuccess());
    }

    private void onError() {
        handler.post(() -> view.onError(R.string.on_error_select_city));
    }
}
