package com.example.newsapp.ViewModels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsapp.CommunicationManager;
import com.example.newsapp.DataRepository;
import com.example.newsapp.Interfaces.NewsApiRetrofit;
import com.example.newsapp.Interfaces.WeatherApiRetrofit;
import com.example.newsapp.Models.NewsModel;
import com.example.newsapp.Models.WeatherModel;
import com.example.newsapp.NewsDataBase.DatabaseHelper;
import com.example.newsapp.Retrofit.RetrofitApiUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<List<NewsModel>> data = new MutableLiveData<>();
    private MutableLiveData<List<NewsModel>> allDataSearched;
    private DatabaseHelper databaseHelper;
    private static String NewsBaseUrl = "https://newsapi.org";
    private static String NewsKey = "3cf0688151764816b34a04d9368ba366";
    public NewsViewModel(@NonNull Application application) {
        super(application);
       // databaseHelper = new DatabaseHelper(application.getApplicationContext());
        context = application.getApplicationContext();
    }



    public MutableLiveData<List<NewsModel>> getData(String category, String language){
        if(data==null){
            //get from retrofit
            data = new MutableLiveData<List<NewsModel>>();
            //LoadListFromData(category,language);
            LoadFromCommunication(category,language);
        }
        return data;
    }

    /*
    public MutableLiveData<List<NewsModel>> getSearchedNews(String search){
        if(allDataSearched==null){
            allDataSearched = databaseHelper.getSearchedNews(search);
        }
        return allDataSearched;
    }
*/


    private void LoadFromCommunication(String category, String language){

        CommunicationManager.GetAllNewsFromDataSource(category,language,context,data);
    }
    private void LoadListFromData(String category, String language){
        NewsApiRetrofit api = RetrofitApiUtil.getRetrofitApiNews();
        Call<List<NewsModel>> call = api.getNewsInfo(language,category,NewsKey);
        call.enqueue(new Callback<List<NewsModel>>() {
            @Override
            public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<NewsModel>> call, Throwable t) {

            }
        });


    }


}
