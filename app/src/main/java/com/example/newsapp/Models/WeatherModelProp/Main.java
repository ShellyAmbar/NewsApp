package com.example.newsapp.Models.WeatherModelProp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main {
    @SerializedName("temp")
    @Expose
    private String Temp;
    @SerializedName("temp_min")
    @Expose
    private String TempMin;
    @SerializedName("temp_max")
    @Expose
    private String TempMax;


    public Main(String temp, String tempMin, String tempMax) {
        Temp = temp;
        TempMin = tempMin;
        TempMax = tempMax;
    }

    public Main() {
    }

    public String getTemp() {
        return Temp;
    }

    public void setTemp(String temp) {
        Temp = temp;
    }

    public String getTempMin() {
        return TempMin;
    }

    public void setTempMin(String tempMin) {
        TempMin = tempMin;
    }

    public String getTempMax() {
        return TempMax;
    }

    public void setTempMax(String tempMax) {
        TempMax = tempMax;
    }

    @Override
    public String toString() {
        return "Main{" +
                "Temp='" + Temp + '\'' +
                ", TempMin='" + TempMin + '\'' +
                ", TempMax='" + TempMax + '\'' +
                '}';
    }
}
