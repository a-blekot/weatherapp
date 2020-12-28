package com.anadi.weatherinfo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class UpdateWorker extends Worker {
    private static final String CHANNEL_ID = "com.anadi.weatherinfo.UpdateChannel";

    public UpdateWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        //        int id = (int) getInputData().getLong("notification", 0);

        createNotificationChannel();
        sendNotification(0);

        return Result.success();
    }

    private void sendNotification(int id) {
        Intent intent = new Intent(getApplicationContext(), com.anadi.weatherinfo.UpdateReceiver.class);
        //        Intent intent = new Intent(getApplicationContext(), com.anadi.weatherinfo.details.DetailsActivity
        //        .class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(UpdateReceiver.NOTIFICATION_ID, id);
        //        intent.putExtra("id", 1);
        intent.setAction(UpdateReceiver.NOTIFICATION);

        // Create the PendingIntent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent,
                                                                 PendingIntent.FLAG_UPDATE_CURRENT);
        //        PendingIntent pendingIntent = PendingIntent.getActivity(
        //                getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID).setSmallIcon(R.drawable.ic_update)
                                                                                                                .setContentTitle("Weather Info")
                                                                                                                .setContentText("Do you  want to update info?")
                                                                                                                .addAction(R.drawable.ic_update, "Ok", pendingIntent) // #0
                                                                                                                .setContentIntent(pendingIntent)
                                                                                                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat.from(getApplicationContext()).notify(id, builder.build());

        //        NotificationManager notificationManager = (NotificationManager)getApplicationContext().
        //                getSystemService(Context.NOTIFICATION_SERVICE);
        //        notificationManager.notify(id, builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getApplicationContext().getString(R.string.channel_name);
            String description = getApplicationContext().getString(R.string.channel_description);

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            //            channel.setVibrationPattern();
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLightColor(getApplicationContext().getResources().getColor(R.color.colorAccent, null));

            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}