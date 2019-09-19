package com.example.newsapp.Models.NewsModelProp;

import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Source {

    @SerializedName("name")
    @Expose
    private String SourceName;


    public Source(String sourceName) {
        SourceName = sourceName;
    }

    public String getSourceName() {
        return SourceName;
    }

    public void setSourceName(String sourceName) {
        SourceName = sourceName;
    }

    public Source() {
    }

    @Override
    public String toString() {
        return "Source{" +
                "SourceName='" + SourceName + '\'' +
                '}';
    }
}
