package com.example.newsapp.Interfaces;

import com.example.newsapp.Models.NewsModel;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiRetrofit {

    @GET("/v2/top-headlines?")
    Call<List<NewsModel>> getNewsInfo(@Query("country") String countryCode , @Query("category") String category,@Query("apiKey") String apiKey);

}
