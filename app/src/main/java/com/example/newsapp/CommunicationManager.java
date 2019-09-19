package com.example.newsapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.newsapp.Models.NewsModel;
import com.example.newsapp.Models.NewsModelProp.Source;
import com.example.newsapp.Models.WeatherModel;
import com.example.newsapp.Models.WeatherModelProp.Main;
import com.example.newsapp.Models.WeatherModelProp.Weather;
import com.example.newsapp.Models.WeatherModelProp.Wind;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public  class CommunicationManager {
    private HashMap<Integer,String> hashMap;

    private static String Whether_Key = "25d8b53d4051e82da7698114960eb870";
    private static String WeatherBaseUrl = "https://api.openweathermap.org";
    private static String NewsBaseUrl = "https://newsapi.org";
    private MutableLiveData<List<WeatherModel>> dataWeather;
    private MutableLiveData<List<NewsModel>> dataNews;
    private List<NewsModel>  newsModelList;
    private List<WeatherModel> weatherModels;

    public CommunicationManager(){
        hashMap = new HashMap<>();
        hashMap.put(0,"ar");
        hashMap.put(1,"au");
        hashMap.put(2,"at");
        hashMap.put(3,"be");
        hashMap.put(4,"br");
        hashMap.put(5,"bg");
        hashMap.put(6,"ca");
        hashMap.put(7,"cn");
        hashMap.put(8,"co");
        hashMap.put(9,"cu");
        hashMap.put(10,"cz");
        hashMap.put(11,"eg");
        hashMap.put(12,"fr");
        hashMap.put(13,"de");
        hashMap.put(14,"gr");
        hashMap.put(15,"hk");
        hashMap.put(16,"hu");
        hashMap.put(17,"in");
        hashMap.put(18,"id");
        hashMap.put(19,"ie");
        hashMap.put(20,"il");
        hashMap.put(21,"it");
        hashMap.put(22,"jp");
        hashMap.put(23,"lv");
        hashMap.put(24,"lt");
        hashMap.put(25,"my");
        hashMap.put(26,"mx");
        hashMap.put(27,"ma");
        hashMap.put(28,"nl");
        hashMap.put(29,"nz");
        hashMap.put(30,"ng");
        hashMap.put(31,"no");
        hashMap.put(32,"ph");
        hashMap.put(33,"pl");
        hashMap.put(34,"pt");
        hashMap.put(35,"ro");
        hashMap.put(36,"ru");
        hashMap.put(37,"sa");
        hashMap.put(38,"rs");
        hashMap.put(39,"sg");
        hashMap.put(40,"sk");
        hashMap.put(41,"si");
        hashMap.put(42,"za");
        hashMap.put(43,"kr");
        hashMap.put(44,"se");
        hashMap.put(45,"ch");
        hashMap.put(46,"tw");
        hashMap.put(47,"th");
        hashMap.put(48,"tr");
        hashMap.put(49,"ae");
        hashMap.put(50,"ua");
        hashMap.put(51,"gb");
        hashMap.put(52,"us");
        hashMap.put(53,"ve");



    }






    public static void GetAllNewsFromDataSource(String categoryId, String countryId, Context context, final MutableLiveData<List<NewsModel>> dataNews)
    {

        final List<NewsModel>  newsModelList = new ArrayList<>();
        String Link =  "https://newsapi.org/v2/top-headlines?country="+countryId+"&category="+categoryId+"&apiKey=3cf0688151764816b34a04d9368ba366";
        RequestQueue queue = Volley.newRequestQueue(context);



        StringRequest getRequest = new StringRequest(Request.Method.GET, Link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject rootObject = new JSONObject(response);
                    JSONArray resultArray = rootObject.getJSONArray("articles");
                    for (int i=0; i<20;i++){
                        JSONObject currentObject = resultArray.getJSONObject(i);
                        String author = currentObject.getString("author");
                        String title = currentObject.getString("title");
                        String description = currentObject.getString("description");
                        String url = currentObject.getString("url");
                        String urlToImage = currentObject.getString("urlToImage");
                        String publishedAt = currentObject.getString("publishedAt");
                        JSONObject source = currentObject.getJSONObject("source");
                        String sourceName = source.getString("name");
                        String key = System.currentTimeMillis()+"";

                        NewsModel newsModel = new NewsModel(urlToImage,title,description,publishedAt,author,url,new Source(sourceName));
                        newsModelList.add(newsModel);
                    }

                    dataNews.setValue(newsModelList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(getRequest);

    }







    public static void   GetWeatherFromDataSource(double lat, double lon, Context context, final MutableLiveData<List<WeatherModel>> dataWeather)
    {
        final WeatherModel[] weatherModel = {null};
        final List<WeatherModel> weatherModels = new ArrayList<>();
        String Link = WeatherBaseUrl + "/data/2.5/forecast?lat="+lat +"&lon="+lon;
        RequestQueue queue = Volley.newRequestQueue(context);


        StringRequest getRequest = new StringRequest(Request.Method.GET, Link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // display response
                String description="";
                String temp="";
                String temp_min="";
                String temp_max="";
                String speed="";
                String deg="";
                String icon="";
                String date = "";
                try {
                    JSONObject rootObject = new JSONObject(response);


                    JSONArray jsonArray = rootObject.getJSONArray("list");

                    for(int i=0;i<jsonArray.length();i++){

                        date  = jsonArray.getJSONObject(i).getString("dt_txt");
                        JSONObject weather  = jsonArray.getJSONObject(i).getJSONObject("weather");
                        description  = weather.getString("description");
                        icon =weather.getString("icon");

                        JSONObject jsonObjectMain = jsonArray.getJSONObject(i).getJSONObject("main");
                        temp = jsonObjectMain.getString("temp");
                        temp_min = jsonObjectMain.getString("temp_min");
                        temp_max = jsonObjectMain.getString("temp_max");



                        JSONObject jsonObjectWind = jsonArray.getJSONObject(i).getJSONObject("wind");
                        speed = jsonObjectWind.getString("speed");
                        deg = jsonObjectWind.getString("deg");

                        JSONObject jsonObjectCity = rootObject.getJSONObject("city");

                        String city = jsonObjectCity.getString("name");

                        Main main = new Main(temp,temp_min,temp_max);
                        Weather weather1 = new Weather(icon,description);
                        Wind wind = new Wind(speed,deg);

                        weatherModel[0] = new WeatherModel(date,main,weather1,wind);
                        weatherModels.add(weatherModel[0]);

                    }




                         dataWeather.setValue(weatherModels);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(getRequest);
       // queue.start();

    }



    public String GetCountryCode(Integer index)
    {
        return hashMap.get(index);

    }

    public void   GetAllNewsFromDataSource(String categoryId, String countryId, Context context)
    {

        newsModelList = new ArrayList<>();
        String Link =  "https://newsapi.org/v2/top-headlines?country="+countryId+"&category="+categoryId+"&apiKey=3cf0688151764816b34a04d9368ba366";
        RequestQueue queue = Volley.newRequestQueue(context);



        StringRequest getRequest = new StringRequest(Request.Method.GET, Link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject rootObject = new JSONObject(response);
                    JSONArray resultArray = rootObject.getJSONArray("articles");
                    for (int i=0; i<10;i++){
                        JSONObject currentObject = resultArray.getJSONObject(i);
                        String author = currentObject.getString("author");
                        String title = currentObject.getString("title");
                        String description = currentObject.getString("description");
                        String url = currentObject.getString("url");
                        String urlToImage = currentObject.getString("urlToImage");
                        String publishedAt = currentObject.getString("publishedAt");
                        JSONObject source = currentObject.getJSONObject("source");
                        String sourceName = source.getString("name");
                        String key = System.currentTimeMillis()+"";

                        NewsModel newsModel = new NewsModel(urlToImage,title,description,publishedAt,author,url,new Source(sourceName));
                        newsModelList.add(newsModel);

                    }





                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(getRequest);
        queue.start();


    }
}
