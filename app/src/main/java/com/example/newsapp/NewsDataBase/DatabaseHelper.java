package com.example.newsapp.NewsDataBase;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.newsapp.Interfaces.NewsDao;
import com.example.newsapp.Models.NewsModel;

import java.util.List;

public class DatabaseHelper {
    /*
    private NewsDao newsDao;
    public DatabaseHelper(Context context){
        NewsLocalDatabase database = NewsLocalDatabase.GetInstance(context);
        newsDao = database.GetNewsDao();
    }

    public MutableLiveData<List<NewsModel>> getSearchedNews(String searchedString){
        return newsDao.getSelectedNews(searchedString);
    }
    public MutableLiveData<List<NewsModel>> getAllNews(){
        return newsDao.getAllNews();
    }
    public void insertNews(NewsModel... newsModels){
        newsDao.Insert(newsModels);
    }
    public void deleteNews(NewsModel... newsModels){
        newsDao.Delete(newsModels);
    }
    public void updateNews(NewsModel... newsModels){
        newsDao.Update(newsModels);
    }

*/
}
