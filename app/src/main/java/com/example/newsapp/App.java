package com.example.newsapp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

public class App extends Application {
    public static final String CHANNEL_ID = "newsNotifications";
    @Override
    public void onCreate() {
        super.onCreate();

        CreateNotificationsChannel();

    }

    private void CreateNotificationsChannel() {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O ){
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "News notification",
                    NotificationManager.IMPORTANCE_HIGH

            );
            Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            channel.enableVibration(true);
            channel.setLightColor(Color.BLUE);
            channel.enableLights(true);
            channel.setSound(uri,null);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

}
