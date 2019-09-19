package com.example.newsapp.Interfaces;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.newsapp.Models.NewsModel;

import java.util.List;


public interface NewsDao {
/*
   // @Query("SELECT * FROM newsmodel WHERE BodyLine LIKE :search  ")
    MutableLiveData<List<NewsModel>> getSelectedNews(String search);

   // @Query("SELECT * FROM newsmodel ")
    MutableLiveData<List<NewsModel>> getAllNews();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void Insert(NewsModel... newsModel);

    @Update
    void Update(NewsModel... newsModel);

    @Delete
    void Delete(NewsModel... newsModel);
*/
}
