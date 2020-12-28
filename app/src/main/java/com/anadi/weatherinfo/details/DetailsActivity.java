package com.anadi.weatherinfo.details;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anadi.weatherinfo.R;
import com.anadi.weatherinfo.repository.IconMap;
import com.anadi.weatherinfo.repository.data.WeatherInfo;

import java.text.DateFormat;
import java.util.Date;

import timber.log.Timber;

public class DetailsActivity extends AppCompatActivity implements DetailsContract.View {

    private DetailsContract.Presenter presenter;
    private int id;

    private ImageView weatherIconIv;
    private ImageView windDegreeIv;
    private TextView locationNameTv;
    private TextView timezoneTv;
    private TextView mainTempTv;
    private TextView windSpeedTv;
    private TextView tempFeelsLikeTv;
    private TextView mainPressureTv;
    private TextView mainHumidityTv;
    private TextView sysSunriseTv;
    private TextView sysSunsetTv;
    private TextView coordinatesTv;

    private ProgressBar progressBar;

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

        int minutes = info.timezone / 60;
        int hours = minutes / 60;
        minutes %= 60; // to get right time in format hh:mm.

        String sign = (hours > 0) ? "+" : (hours < 0) ? "-" : "";
        hours = Math.abs(hours);
        timezoneTv.setText(getString(R.string.timezone, sign, hours, minutes));
        mainTempTv.setText(getString(R.string.temp_celsium, info.main.temp));
        windSpeedTv.setText(getString(R.string.wind_speed_ms, info.wind.speed));
        tempFeelsLikeTv.setText(getString(R.string.feels_like_celsium, info.main.feelsLike));
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

        ObjectAnimator animation = ObjectAnimator.ofFloat(windDegreeIv, "rotation", windDegreeIv.getRotation() - 720f);
        animation.setDuration(500);
        animation.start();

        createAnimation();

        presenter.update();
    }

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
        tempFeelsLikeTv = findViewById(R.id.details_temp_feels_like);
        mainPressureTv = findViewById(R.id.details_main_pressure);
        mainHumidityTv = findViewById(R.id.details_main_humidity);
        sysSunriseTv = findViewById(R.id.details_sys_sunrise);
        sysSunsetTv = findViewById(R.id.details_sys_sunset);
        coordinatesTv = findViewById(R.id.details_coordinates);

        progressBar = findViewById(R.id.progress_update);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        presenter = new DetailsPresenter(this, id);
        presenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        presenter.unsubscribe();
        super.onDestroy();
    }

    private void createAnimation() {
        if (weatherIconIv == null) {
            return;
        }

        switch (weatherIconIv.getVisibility()) {
            case View.VISIBLE:
                animationHideImage();
                break;

            //            case View.INVISIBLE:
            //                animationShowImage();
            //                break;
        }
    }

    private void animationShowImage() {
        // Get the center for the clipping circle.
        int cx = weatherIconIv.getWidth() / 2;
        int cy = weatherIconIv.getHeight() / 2;

        // Get the final radius for the clipping circle.
        float finalRadius = (float) Math.hypot(cx, cy);

        // Create the animator for this view (the start radius is zero).
        Animator anim = ViewAnimationUtils.createCircularReveal(weatherIconIv, cx, cy, 0, finalRadius);

        // Make the view visible and start the animation.
        weatherIconIv.setVisibility(View.VISIBLE);
        anim.start();
    }

    private void animationHideImage() {
        // Previously visible view

        // Get the center for the clipping circle.
        int cx = weatherIconIv.getWidth() / 2;
        int cy = weatherIconIv.getHeight() / 2;

        // Get the initial radius for the clipping circle.
        float initialRadius = (float) Math.hypot(cx, cy);

        // Create the animation (the final radius is zero.
        Animator anim = ViewAnimationUtils.
                                                  createCircularReveal(weatherIconIv, cx, cy, initialRadius, 0);

        // Make the view invisible when the animation is done.
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                weatherIconIv.setVisibility(View.INVISIBLE);
                animationShowImage();
            }
        });

        // Start the animation.
        anim.start();
    }
}