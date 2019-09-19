package com.example.newsapp.Retrofit;

import com.example.newsapp.Interfaces.NewsApiRetrofit;
import com.example.newsapp.Interfaces.WeatherApiRetrofit;
import com.example.newsapp.Models.NewsModel;
import com.example.newsapp.Models.WeatherModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApiUtil {
    //private static String Whether_Key = "25d8b53d4051e82da7698114960eb870";
    private static String WeatherBaseUrl = "https://api.openweathermap.org";
    private static String NewsBaseUrl = "https://newsapi.org";
    private static final Type NEWS_ARRAY_TYPE = (new ArrayList<NewsModel>()).getClass();
    private static final Type WEATHER_ARRAY_TYPE = (new ArrayList<WeatherModel>()).getClass();

    public static WeatherApiRetrofit getRetrofitApiWeather(){

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(WEATHER_ARRAY_TYPE,new WeatherGsonDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeatherBaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();

        WeatherApiRetrofit apiRetrofit = retrofit.create(WeatherApiRetrofit.class);
        return  apiRetrofit;
    }

    public static NewsApiRetrofit getRetrofitApiNews(){

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(NEWS_ARRAY_TYPE,new NewsGsonDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NewsBaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();

        NewsApiRetrofit apiRetrofit = retrofit.create(NewsApiRetrofit.class);
        return  apiRetrofit;
    }



}
