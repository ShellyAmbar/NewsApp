package com.example.newsapp.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.newsapp.Adapters.WeatherAdapter;
import com.example.newsapp.CommunicationManager;
import com.example.newsapp.DataRepository;
import com.example.newsapp.Interfaces.ItemListner;
import com.example.newsapp.LiveData.CurrentLocationListener;
import com.example.newsapp.Models.NewsModel;
import com.example.newsapp.Models.WeatherModel;
import com.example.newsapp.Models.WeatherModelProp.Main;
import com.example.newsapp.Models.WeatherModelProp.Weather;
import com.example.newsapp.Models.WeatherModelProp.Wind;
import com.example.newsapp.NewsDataDialogFragment;
import com.example.newsapp.R;
import com.example.newsapp.ViewModels.WeatherViewModel;
import com.example.newsapp.WeatherDataDialogFragment;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WheatherFragment extends Fragment implements ItemListner {

    private  List<WeatherModel> weatherModel;
    private final int LOCATION_PERMISSION_REQUEST = 100;
    private WeatherViewModel viewModel;
    private List<WeatherModel> weatherModels;
    private RecyclerView recyclerView;
    private WeatherAdapter weatherAdapter;
    private static String WeatherBaseUrl = "https://api.openweathermap.org";
    private static String api_weather_apixe = "91006963277a46dbbf6103349190909";
    private double lat,lon;




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wheather_fragment,container,false);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView = view.findViewById(R.id.weather_recycler);
        recyclerView.setLayoutManager(manager);

        CommunicationManager communicationManager = new CommunicationManager();
        weatherModels = new ArrayList<>();
        weatherAdapter = new WeatherAdapter(weatherModels,getContext(),this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(weatherAdapter);

        getWeatherFromLocation();

        //getWeatherFromLocation();

        if(lat!=0 && lon!=0){

            viewModel.getWeatherLiveData(lat,lon,getContext()).observe(this, new Observer<List<WeatherModel>>() {
                @Override
                public void onChanged(List<WeatherModel> weatherModelsChanged) {
                    weatherModels = weatherModelsChanged;
                    weatherAdapter.notifyDataSetChanged();

                }
            });

        }

        return view;


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public  void getWeatherFromLocation(){


        String city = null;

        if(Build.VERSION.SDK_INT>=23){
            int permission = getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            if(permission!= PackageManager.PERMISSION_GRANTED){

                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_PERMISSION_REQUEST);
            }else getLocationUpdates();
        }else getLocationUpdates();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == LOCATION_PERMISSION_REQUEST){

            if(grantResults[0]!= PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getContext(), "Sorry, a problem with your location permissions occurred, Try again soon.", Toast.LENGTH_SHORT).show();
            }else{

                getLocationUpdates();
            }
        }
    }

    private void getLocationUpdates(){
        final String country = "";
        CurrentLocationListener.getInstance(getContext()).observe(this, new Observer<Location>() {
            @Override
            public void onChanged(Location location) {


                lat = location.getLatitude();
                lon = location.getLongitude();
                GetWeatherFromDataSource(lat,lon,getContext());





            }
        });



    }


    public  void GetWeatherFromDataSource(double lat, final double lon, Context context)
    {
        if(lat!=0&&lon!=0) {
            String Whether_Key = "25d8b53d4051e82da7698114960eb870";

            weatherModels = new ArrayList<>();
            String Link = WeatherBaseUrl + "/data/2.5/forecast?lat=" + lat + "&lon=" + lon + "&appid=" + Whether_Key;
            RequestQueue queue = Volley.newRequestQueue(context);


            StringRequest getRequest = new StringRequest(Request.Method.GET, Link, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    WeatherModel weatherModel = null;
                    // display response
                    String description = "";
                    String temp = "";
                    String temp_min = "";
                    String temp_max = "";
                    String speed = "";
                    String deg = "";
                    String icon = "";
                    String date = "";
                    try {
                        JSONObject rootObject = new JSONObject(response);


                        JSONArray jsonArray = rootObject.getJSONArray("list");

                        for (int i = 0; i < 10; i++) {

                            date = jsonArray.getJSONObject(i).getString("dt_txt");
                            JSONObject weather = jsonArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0);
                            description = weather.getString("description");
                            icon = weather.getString("icon");

                            JSONObject jsonObjectMain = jsonArray.getJSONObject(i).getJSONObject("main");
                            temp = jsonObjectMain.getString("temp");
                            temp_min = jsonObjectMain.getString("temp_min");
                            temp_max = jsonObjectMain.getString("temp_max");


                            JSONObject jsonObjectWind = jsonArray.getJSONObject(i).getJSONObject("wind");
                            speed = jsonObjectWind.getString("speed");
                            deg = jsonObjectWind.getString("deg");

                            JSONObject jsonObjectCity = rootObject.getJSONObject("city");

                            String city = jsonObjectCity.getString("name");

                            try {

                                float temperature = Float.parseFloat(temp);
                                temperature -= 273;
                                temp = String.valueOf(temperature);

                                float temperature_min = Float.parseFloat(temp_min);
                                temperature_min -= 273;
                                temp_min = String.valueOf(temperature_min);

                                float temperature_max = Float.parseFloat(temp_max);
                                temperature_max -= 273;
                                temp_max = String.valueOf(temperature_max);

                            } catch (NumberFormatException nf) {

                                System.out.println(nf.getStackTrace().toString() + "parse");
                            }


                            Main main = new Main(temp, temp_min, temp_max);
                            Weather weather1 = new Weather(icon, description);
                            Wind wind = new Wind(speed, deg);

                            weatherModel = new WeatherModel(date, main, weather1, wind);
                            weatherModels.add(weatherModel);

                        }
                        weatherAdapter.setNewList(weatherModels);
                        weatherAdapter.notifyDataSetChanged();

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

    }

    @Override
    public void onClickItem(int pos) {
        ShowDialog(weatherModels.get(pos));
    }

    @Override
    public void onClickShare(int pos) {

    }
    public void ShowDialog(WeatherModel weatherModel){
        Intent intent = new Intent(getActivity(),WeatherDataDialogFragment.class);
        intent.putExtra("one",weatherModel.getDate());
        intent.putExtra("two",weatherModel.getWeather().getDescription());
        intent.putExtra("tree","Temperature: "+weatherModel.getMain().getTemp());
        intent.putExtra("four","Max Temperature: "+weatherModel.getMain().getTempMax());
        intent.putExtra("five","Wind speed: "+weatherModel.getWind().getSpeed());
        intent.putExtra("six","Minimum Temperature: "+weatherModel.getMain().getTempMin());
        intent.putExtra("seven",weatherModel.getWeather().getIcon());
        intent.putExtra("url","");
        getContext().startActivity(intent);

    }
}
