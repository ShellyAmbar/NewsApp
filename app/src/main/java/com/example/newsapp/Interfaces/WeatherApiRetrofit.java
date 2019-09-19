package com.example.newsapp.Interfaces;

import com.example.newsapp.Models.WeatherModel;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiRetrofit {




    @GET("/data/2.5/forecast?")
    Call<List<WeatherModel>> getWeatherInfo(@Query("lat") double lat, @Query("lon") double lon);

}
