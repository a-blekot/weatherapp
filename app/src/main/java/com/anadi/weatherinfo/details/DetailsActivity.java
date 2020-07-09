package com.anadi.weatherinfo.details;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.anadi.weatherinfo.R;

public class DetailsActivity extends AppCompatActivity implements DetailsContract.View{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
    }

    @Override
    public void loading() {

    }

    @Override
    public void onError(int resId) {

    }

    @Override
    public void onUpdateSuccess() {

    }
}