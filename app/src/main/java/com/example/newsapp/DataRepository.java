package com.example.newsapp;

import android.content.Context;

import com.example.newsapp.Models.NewsModel;
import com.example.newsapp.Models.WeatherModel;

import java.util.HashMap;
import java.util.List;

public class DataRepository {

    private static HashMap<Integer,String> hashMap;

    private static String Whether_Key = "25d8b53d4051e82da7698114960eb870";
    private static String WeatherBaseUrl = "http://api.openweathermap.org";
    private static String NewsBaseUrl = "https://newsapi.org";


    public DataRepository(){
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

    public static String GetCountryCode(Integer index)
    {
        return hashMap.get(index);

    }




}
