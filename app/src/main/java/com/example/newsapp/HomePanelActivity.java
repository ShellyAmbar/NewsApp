package com.example.newsapp;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.newsapp.Fragments.NewsFragment;
import com.example.newsapp.Interfaces.NotificationsListener;
import com.example.newsapp.Models.NewsModel;
import com.example.newsapp.Models.NewsModelProp.Source;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.Toast;

import com.example.newsapp.Adapters.PagerAdapter;
import com.example.newsapp.Models.WeatherModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class HomePanelActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private final int LOCATION_PERMISSION_REQUEST = 100;
    private final String COUNTRY_COD ="country_code";
    FusedLocationProviderClient client;
    private double lat;
    private double lon;
    private WeatherModel weatherModel;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private PagerAdapter pagerAdapter;
    private String countryCod;
    private CommunicationManager communicationManager;
    private SharedPreferences sharedPreferences;
    private AlarmManager alarmManager;
    private int time;
    private int category;
    private List<NewsModel> newsModelList;
    private HashMap<Integer,String> hashMapCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_panel);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        //getWeatherFromLocation();
        //communicationManager = new CommunicationManager();
        viewPager = findViewById(R.id.viewPager_all_news);
        tabLayout = findViewById(R.id.tabLayout);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);
        //tabLayout.setupWithViewPager(viewPager);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {


                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        hashMapCategory = new HashMap<>();
        hashMapCategory.put(0,"business");
        hashMapCategory.put(1,"health");
        hashMapCategory.put(2,"technology");
        hashMapCategory.put(3,"sports");
        hashMapCategory.put(4,"entertainment");
        hashMapCategory.put(5,"science");










    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        time = 0;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_notifications) {

            AlertDialog.Builder builder2 = new AlertDialog.Builder(HomePanelActivity.this);

            builder2.setTitle("Select category").setSingleChoiceItems(R.array.category, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if(time == 12){
                        dialog.dismiss();
                    }else{
                        category = which;
                        Toast.makeText(HomePanelActivity.this, "Notification of  : "+hashMapCategory.get(category) +" category.", Toast.LENGTH_SHORT).show();

                    }




                }
            }).setPositiveButton(getResources().getString(R.string.finish), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                    if(time == 12){
                        dialog.dismiss();
                    }else{

                        StartNotificationService(time,hashMapCategory.get(category));
                        dialog.dismiss();
                    }



                }
            }).show();

            AlertDialog.Builder builder = new AlertDialog.Builder(HomePanelActivity.this);

            builder.setTitle(getResources().getString(R.string.notify_frequency)).setSingleChoiceItems(R.array.frequency, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    time = which;

                }
            }).setPositiveButton(getResources().getString(R.string.finish), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if(time == 12){
                        Toast.makeText(HomePanelActivity.this, "Notification canceled", Toast.LENGTH_SHORT).show();

                        CancleNotificationService();
                    }

                    dialog.dismiss();
                }
            }).show();

        } else if (id == R.id.nav_country) {



            AlertDialog.Builder builder = new AlertDialog.Builder(HomePanelActivity.this);

            builder.setTitle(getResources().getString(R.string.choose_country)).setSingleChoiceItems(R.array.countries, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CommunicationManager communicationManager = new CommunicationManager();
                    countryCod = communicationManager.GetCountryCode(which);
                }
            }).setPositiveButton(getResources().getString(R.string.finish), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences.Editor editor =  sharedPreferences.edit();
                    editor.putString("CountryCode",countryCod);
                    editor.commit();

                    dialog.dismiss();
                }
            }).show();





        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void CancleNotificationService() {

        Intent serviceIntent = new Intent(this, NewsNotificationsService.class);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(HomePanelActivity.this,100,serviceIntent,PendingIntent.FLAG_CANCEL_CURRENT);

            alarmManager.cancel(pendingIntent);
            Toast.makeText(this, "Notifications frequency canceled" , Toast.LENGTH_SHORT).show();


    }

    private void StartNotificationService(int time, String category){



        GetAllNewsFromDataSource(category,sharedPreferences.getString("CountryCode","us"),this);




    }


    public void   GetAllNewsFromDataSource(String categoryId, String countryId, Context context)
    {

        newsModelList = new ArrayList<>();
        String Link =  "https://newsapi.org/v2/top-headlines?country="+countryId+"&category="+categoryId+"&apiKey=3cf0688151764816b34a04d9368ba366";
        RequestQueue queue = Volley.newRequestQueue(context);



        StringRequest getRequest = new StringRequest(Request.Method.GET, Link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject rootObject = new JSONObject(response);
                    JSONArray resultArray = rootObject.getJSONArray("articles");
                    for (int i=0; i<1;i++){
                        JSONObject currentObject = resultArray.getJSONObject(i);
                        String author = currentObject.getString("author");
                        String title = currentObject.getString("title");
                        String description = currentObject.getString("description");
                        String url = currentObject.getString("url");
                        String urlToImage = currentObject.getString("urlToImage");
                        String publishedAt = currentObject.getString("publishedAt");
                        JSONObject source = currentObject.getJSONObject("source");
                        String sourceName = source.getString("name");
                        String key = System.currentTimeMillis()+"";

                        NewsModel newsModel = new NewsModel(urlToImage,title,description,publishedAt,author,url,new Source(sourceName));
                        newsModelList.add(newsModel);

                    }

                    setNotificationAlert(newsModelList.get(0));




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(getRequest);
        queue.start();


    }


    private void setNotificationAlert(NewsModel model){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        //put in alarm
        Intent serviceIntent = new Intent(this, NewsNotificationsService.class);

        serviceIntent.putExtra("time",time+1);
        serviceIntent.putExtra("headline",model.getHeadline());
        serviceIntent.putExtra("image",model.getImageUrl());


        PendingIntent pendingIntent = PendingIntent.getBroadcast(HomePanelActivity.this,100,serviceIntent,PendingIntent.FLAG_CANCEL_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()+(time+1)*1000,pendingIntent);
            Toast.makeText(this, "Alarm set with frequency of : "+ (time+1) +"seconds" , Toast.LENGTH_SHORT).show();
        }else{
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()+(time+1)*1000,(time+1)*1000,pendingIntent);
            Toast.makeText(this, "Alarm set with frequency of : "+ (time+1) +"seconds" , Toast.LENGTH_SHORT).show();

        }
    }

}
