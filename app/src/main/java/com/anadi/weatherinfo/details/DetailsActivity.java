package com.anadi.weatherinfo.details;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anadi.weatherinfo.R;
import com.anadi.weatherinfo.repository.IconMap;
import com.anadi.weatherinfo.repository.data.WeatherInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import timber.log.Timber;

public class DetailsActivity extends AppCompatActivity implements DetailsContract.View{

    private DetailsContract.Presenter presenter;
    private int id;
    
    private ImageView weatherIconIv;
    private ImageView windDegreeIv;
    private TextView locationNameTv;
    private TextView timezoneTv;
    private TextView mainTempTv;
    private TextView windSpeedTv;
    private TextView weatherMainTv;
    private TextView tempFeelsLikeTv;
    private TextView weatherDescriptionTv;
    private TextView mainPressureTv;
    private TextView mainHumidityTv;
    private TextView sysSunriseTv;
    private TextView sysSunsetTv;
    private TextView coordinatesTv;
    
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        weatherIconIv = findViewById(R.id.details_weather_icon);
        windDegreeIv = findViewById(R.id.details_wind_degree);
        locationNameTv = findViewById(R.id.details_location_name);
        timezoneTv = findViewById(R.id.details_timezone);
        mainTempTv = findViewById(R.id.details_main_temp);
        windSpeedTv = findViewById(R.id.details_wind_speed);
        weatherMainTv = findViewById(R.id.details_weather_main);
        tempFeelsLikeTv = findViewById(R.id.details_temp_feels_like);
        weatherDescriptionTv = findViewById(R.id.details_weather_description);
        mainPressureTv = findViewById(R.id.details_main_pressure);
        mainHumidityTv = findViewById(R.id.details_main_humidity);
        sysSunriseTv = findViewById(R.id.details_sys_sunrise);
        sysSunsetTv = findViewById(R.id.details_sys_sunset);
        coordinatesTv = findViewById(R.id.details_coordinates);

        progressBar = findViewById(R.id.progress_update);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        presenter = new DetailsPresenter(this, id);
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
    public void onUpdateSuccess() {
        progressBar.setVisibility(View.GONE);

        WeatherInfo info = presenter.getInfo(id);
        if (info == null) {
            Timber.d("WeatherInfo is null!");
            return;
        }

        weatherIconIv.setImageResource(IconMap.getIconId(info.weather.get(0).icon));

        windDegreeIv.setRotation(info.wind.deg);
        locationNameTv.setText(getString(R.string.details_location_name, info.name, info.sys.country));

        int minutes = info.timezone/60;
        int hours = minutes/60;
        minutes %= 60; // to get right time in format hh:mm.

        String sign = (hours > 0) ? "+" : (hours < 0) ? "-" : "";
        hours = Math.abs(hours);
        timezoneTv.setText(getString(R.string.timezone, sign, hours, minutes));
        mainTempTv.setText(getString(R.string.temp_celsium, info.main.temp));
        windSpeedTv.setText(getString(R.string.wind_speed_ms, info.wind.speed));
//        weatherMainTv.setText(info.weather.get(0).main);
        tempFeelsLikeTv.setText(getString(R.string.feels_like_celsium, info.main.feelsLike));
//        weatherDescriptionTv.setText(info.weather.get(0).description);
        mainPressureTv.setText(getString(R.string.pressure, info.main.pressure));
        mainHumidityTv.setText(getString(R.string.humidity, info.main.humidity));

//        SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
        DateFormat format = DateFormat.getTimeInstance();
        Date sunrise = new Date(info.sys.sunrise * 1000);
        Date sunset = new Date(info.sys.sunset * 1000);

        sysSunriseTv.setText(getString(R.string.sunrise, format.format(sunrise)));
        sysSunsetTv.setText(getString(R.string.sunset, format.format(sunset)));
        coordinatesTv.setText(getString(R.string.coordinates, info.coord.lat, info.coord.lon));
    }

    public void update(View view) {
        presenter.update();
    }
}