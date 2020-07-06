package com.anadi.weatherinfo.addlocation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.anadi.weatherinfo.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AddLocationActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener, AddLocationContract.View {

    private AddLocationContract.Presenter presenter;

    private Spinner countrySpinner;
    private Spinner citySpinner;

    private ProgressBar progressBar;

    private String selectedCountry;
    private String selectedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        presenter = new AddLocationPresenter(this);

        countrySpinner = findViewById(R.id.country_spinner);
        citySpinner = findViewById(R.id.city_spinner);
        progressBar = findViewById(R.id.progress);

        countrySpinner.setOnItemSelectedListener(this);
        citySpinner.setOnItemSelectedListener(this);

        countrySpinner.setAdapter(new ArrayAdapter<String>(
                this, R.layout.spinner_dropdown_item, presenter.getCountryNames() ));
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


    @Override
    public void updateCityList(@NotNull ArrayList<String> cities) {
        citySpinner.setAdapter(new ArrayAdapter<String>(
                this, R.layout.spinner_dropdown_item, cities));
    }

    public void addLocation(View view) {
        presenter.addLocation(selectedCity, selectedCountry);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.country_spinner) {
            selectedCountry = countrySpinner.getItemAtPosition(position).toString();

            if(!TextUtils.isEmpty(selectedCountry) &&
               !selectedCountry.equalsIgnoreCase("Select Item")) {
                presenter.onCountrySelected(selectedCountry);
            }
        }

        if (parent.getId() == R.id.city_spinner) {
            selectedCity = citySpinner.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

