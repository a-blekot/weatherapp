package com.anadi.weatherinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;

import com.anadi.weatherinfo.addlocation.AddLocationContract;
import com.anadi.weatherinfo.addlocation.LocationsProvider;
import com.anadi.weatherinfo.repository.Country;
import com.anadi.weatherinfo.repository.Locations;
import com.anadi.weatherinfo.repository.LocationsCash;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import timber.log.Timber;

public class UpdateReceiver extends BroadcastReceiver {

    public static final String NOTIFICATION_ID = "notification-id";
    public static final String NOTIFICATION = "notification";
    private LocationsProvider locationsProvider = Locations.getInstance();
    private AddLocationContract.Model model = LocationsCash.getInstance();
    private Executor exec = Executors.newSingleThreadExecutor();

    @Override
    public void onReceive(Context context, Intent intent) {
//        String intentAction = intent.getAction();
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);

        if (id == 0) {
            String toastMessage = "add random location";
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();

            Country country = locationsProvider.getRandomCountry();
            String cityName = locationsProvider.getRandomCity(country.getName());

            exec.execute(() -> {
                try {
                    boolean result = model.add(cityName, country.getName());

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    e.printStackTrace();
                }
            });

            return;
        }
    }
}
