package com.example.newsapp.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.newsapp.Models.NewsModelProp.Source;
import com.example.newsapp.Models.WeatherModelProp.Weather;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class NewsModel {


    @SerializedName("urlToImage")
    private String ImageUrl;
    @SerializedName("title")
    private String Headline;
    @SerializedName("description")
    private String BodyLine;
    @SerializedName("publishedAt")
    private String Date;
    @SerializedName("author")
    private String Author;
    @SerializedName("url")
    private String Link;

    @SerializedName("source")
    @Expose
    private Source source;


    public NewsModel(String imageUrl, String headline, String bodyLine, String date, String author, String link, Source source) {
        ImageUrl = imageUrl;
        Headline = headline;
        BodyLine = bodyLine;
        Date = date;
        Author = author;
        Link = link;
        this.source = source;
    }

    public NewsModel() {
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getHeadline() {
        return Headline;
    }

    public void setHeadline(String headline) {
        Headline = headline;
    }

    public String getBodyLine() {
        return BodyLine;
    }

    public void setBodyLine(String bodyLine) {
        BodyLine = bodyLine;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }
}
