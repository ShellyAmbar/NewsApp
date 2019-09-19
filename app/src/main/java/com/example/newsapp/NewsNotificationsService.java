package com.example.newsapp;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;
import android.widget.RemoteViews;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.newsapp.Models.NewsModel;
import com.example.newsapp.Models.NewsModelProp.Source;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.newsapp.App.CHANNEL_ID;

public class NewsNotificationsService extends BroadcastReceiver {

    private NewsModel newsModel;



    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, HomePanelActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,notificationIntent,PendingIntent.FLAG_CANCEL_CURRENT);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.notification_layout);
        String image = intent.getStringExtra("image");
        remoteViews.setImageViewResource(R.id.image,R.mipmap.ic_notification_icon );
        // notification's title
        String text = "Click here and stay updated! All the current news in one place.";
        String headline = intent.getStringExtra("headline");
        remoteViews.setTextViewText(R.id.headline, headline);
        remoteViews.setTextViewText(R.id.body,text);


        remoteViews.setOnClickPendingIntent(R.id.headline,pendingIntent);

        Notification notification = new NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_notification_icon)
                .setContentTitle(headline)
                .setContentText(text)
                .setCustomBigContentView(remoteViews)
                .build();


        manager.notify(1,notification);


        long time = (long) intent.getIntExtra("time",0);


        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context,100,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(time+1)*1000,pendingIntent2);
        }else{
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+(time+1)*1000,(time+1)*1000,pendingIntent2);

        }


    }
}
