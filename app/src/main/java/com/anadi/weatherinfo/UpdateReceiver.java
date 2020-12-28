package com.anadi.weatherinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.anadi.weatherinfo.addlocation.AddLocationContract;
import com.anadi.weatherinfo.addlocation.LocationsProvider;
import com.anadi.weatherinfo.repository.Country;
import com.anadi.weatherinfo.repository.Locations;
import com.anadi.weatherinfo.repository.LocationsCash;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UpdateReceiver extends BroadcastReceiver {

    public static final String NOTIFICATION_ID = "notification-id";
    public static final String NOTIFICATION = "notification";
    private final LocationsProvider locationsProvider = Locations.getInstance();
    private final AddLocationContract.Model model = LocationsCash.getInstance();
    private final Executor exec = Executors.newSingleThreadExecutor();

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
