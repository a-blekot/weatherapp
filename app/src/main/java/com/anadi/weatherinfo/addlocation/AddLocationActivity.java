package com.anadi.weatherinfo.addlocation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.anadi.weatherinfo.CitiesCash;
import com.anadi.weatherinfo.Location;
import com.anadi.weatherinfo.R;

import java.util.ArrayList;

import timber.log.Timber;

public class AddLocationActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener, AddLocationView {

    Spinner countrySpinner;
    Spinner citySpinner;
    Button addLocationButton;

    private String selectedCountry;
    private String selectedCity;

    private AddLocationPresenter presenter;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        presenter = new AddLocationPresenter();
        presenter.bind(this);

        ArrayList<String> countries = new ArrayList<String>();

        countries = Location.getCountryNamesArray();


        countrySpinner = findViewById(R.id.country_spinner);
        citySpinner = findViewById(R.id.city_spinner);
        addLocationButton = findViewById(R.id.add_location_button);
        progressBar = findViewById(R.id.progress);

        countrySpinner.setOnItemSelectedListener(this);
        citySpinner.setOnItemSelectedListener(this);

        countrySpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, countries ));
    }

    @Override
    public void onAddedSuccess() {
        progressBar.setVisibility(View.GONE);
        finish();
    }

    @Override
    public void loading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(int resId) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(), getText(resId), Toast.LENGTH_LONG).show();
    }

    public void addLocation(View view) {
        presenter.addLocation(selectedCity, selectedCountry);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.country_spinner) {
            selectedCountry = countrySpinner.getItemAtPosition(position).toString();

            if(!selectedCountry.equalsIgnoreCase("Select Item"))
                setCitySpinner();
        }

        if (parent.getId() == R.id.city_spinner)
            selectedCity = citySpinner.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void setCitySpinner() {
        ArrayList<String> cities = Location.getCityNames(selectedCountry);


        if (null == cities) {

            Timber.d( "EMPTY cities LIST");
            return;
        }
        else
            Timber.d( cities.toString());

        citySpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cities ));
    }
}

