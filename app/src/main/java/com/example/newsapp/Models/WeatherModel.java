package com.example.newsapp.Models;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import com.example.newsapp.Models.WeatherModelProp.Main;
import com.example.newsapp.Models.WeatherModelProp.Weather;
import com.example.newsapp.Models.WeatherModelProp.Wind;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherModel {

    @SerializedName("dt_txt")
    @Expose
    private String Date;

    @SerializedName("main")
    @Expose
    private Main Main;

    @SerializedName("weather")
    @Expose
    private Weather Weather;

    @SerializedName("wind")
    @Expose
    private Wind Wind;


    public WeatherModel(String date, com.example.newsapp.Models.WeatherModelProp.Main main, com.example.newsapp.Models.WeatherModelProp.Weather weather, com.example.newsapp.Models.WeatherModelProp.Wind wind) {
        Date = date;
        Main = main;
        Weather = weather;
        Wind = wind;
    }


    public WeatherModel() {
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public com.example.newsapp.Models.WeatherModelProp.Main getMain() {
        return Main;
    }

    public void setMain(com.example.newsapp.Models.WeatherModelProp.Main main) {
        Main = main;
    }

    public com.example.newsapp.Models.WeatherModelProp.Weather getWeather() {
        return Weather;
    }

    public void setWeather(com.example.newsapp.Models.WeatherModelProp.Weather weather) {
        Weather = weather;
    }

    public com.example.newsapp.Models.WeatherModelProp.Wind getWind() {
        return Wind;
    }

    public void setWind(com.example.newsapp.Models.WeatherModelProp.Wind wind) {
        Wind = wind;
    }

    @Override
    public String toString() {
        return "WeatherModel{" +
                "Date='" + Date + '\'' +
                ", Main=" + Main +
                ", Weather=" + Weather +
                ", Wind=" + Wind +
                '}';
    }
}
