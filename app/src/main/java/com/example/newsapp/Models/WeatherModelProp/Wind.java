package com.example.newsapp.Models.WeatherModelProp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wind {


    @SerializedName("speed")
    @Expose
    private String Speed;
    @SerializedName("deg")
    @Expose
    private String Degree;

    public Wind(String speed, String degree) {
        Speed = speed;
        Degree = degree;
    }

    public Wind() {
    }

    public String getSpeed() {
        return Speed;
    }

    public void setSpeed(String speed) {
        Speed = speed;
    }

    public String getDegree() {
        return Degree;
    }

    public void setDegree(String degree) {
        Degree = degree;
    }

    @Override
    public String toString() {
        return "Wind{" +
                "Speed='" + Speed + '\'' +
                ", Degree='" + Degree + '\'' +
                '}';
    }
}
