package com.example.newsapp.NewsDataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.newsapp.Interfaces.NewsDao;
import com.example.newsapp.Models.NewsModel;
/*
@Database(entities = NewsModel.class, version = 1)
public abstract class NewsLocalDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "database.db";
    private static NewsLocalDatabase instance;
    //private NewsLocalDatabase(){}
    public static NewsLocalDatabase GetInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context,NewsLocalDatabase.class,DATABASE_NAME).allowMainThreadQueries().build();
        }
        return instance;
    }
    public abstract NewsDao GetNewsDao();

}*/
