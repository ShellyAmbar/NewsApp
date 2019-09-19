package com.example.newsapp.ViewModels;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.newsapp.CommunicationManager;
import com.example.newsapp.DataRepository;
import com.example.newsapp.Fragments.WheatherFragment;
import com.example.newsapp.Interfaces.WeatherApiRetrofit;
import com.example.newsapp.LiveData.CurrentLocationListener;
import com.example.newsapp.Models.WeatherModel;
import com.example.newsapp.Retrofit.RetrofitApiUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WeatherViewModel extends AndroidViewModel {
    private Context context;
    private WeatherModel weatherModel;
    private final int LOCATION_PERMISSION_REQUEST = 100;
    private MutableLiveData<List<WeatherModel>> listLiveData;
    private static String Whether_Key = "25d8b53d4051e82da7698114960eb870";
    private static String WeatherBaseUrl = "http://api.openweathermap.org";

    public WeatherViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();

    }



    public MutableLiveData<List<WeatherModel>> getWeatherLiveData(double lat, double lon, Context context) {
        if(listLiveData==null){
            //get from retrofit
            listLiveData = new MutableLiveData<List<WeatherModel>>();
           // LoadListFromData(lat,lon,context);
            LoadFromCommunication(lat,lon,context);
        }

        return listLiveData;
    }

    private void LoadFromCommunication(double lat, double lon, Context context){
        CommunicationManager.GetWeatherFromDataSource(lat,lon,context,listLiveData);
    }

    private void LoadListFromData(double lat, double lon, Context context){
        WeatherApiRetrofit api = RetrofitApiUtil.getRetrofitApiWeather();
        Call<List<WeatherModel>> call = api.getWeatherInfo(lat,lon);
        call.enqueue(new Callback<List<WeatherModel>>() {
            @Override
            public void onResponse(Call<List<WeatherModel>> call, Response<List<WeatherModel>> response) {

                listLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<WeatherModel>> call, Throwable t) {

            }
        });


    }
}
