package com.example.newsapp.Models.WeatherModelProp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather {

    @SerializedName("icon")
    @Expose
    private String Icon;
    @SerializedName("description")
    @Expose
    private String Description;

    public Weather(String icon, String description) {
        Icon = icon;
        Description = description;
    }

    public Weather() {
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "Icon='" + Icon + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }
}
