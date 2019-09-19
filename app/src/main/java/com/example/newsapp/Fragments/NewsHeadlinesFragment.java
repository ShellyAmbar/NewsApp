package com.example.newsapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.newsapp.CommunicationManager;
import com.example.newsapp.Models.NewsModel;
import com.example.newsapp.Models.NewsModelProp.Source;
import com.example.newsapp.NewsNotificationsService;
import com.example.newsapp.R;
import com.example.newsapp.ViewModels.NewsViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.MODE_PRIVATE;

public class NewsHeadlinesFragment extends Fragment {

    private ImageView imageView;
    private TextView textView;
    private SharedPreferences sharedPreferences;
    private List<NewsModel> newsModelList;
    private Handler handler;
    int listSize= 0;
    int counter = 1;
    int iterator =0;

    private NewsViewModel viewModel;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_headlines_fragment,container,false);


        String Language = sharedPreferences.getString("CountryCode","us");

        //get list from viewmodel

        imageView = view.findViewById(R.id.image_headline);
        textView = view.findViewById(R.id.title_headline);

        CommunicationManager communicationManager = new CommunicationManager();
        GetAllNewsFromDataSource("entertainment",Language,getContext());



        //observe on the data source
        viewModel.getData("entertainment",Language).observe(this, new Observer<List<NewsModel>>() {
            @Override
            public void onChanged(List<NewsModel> newsModels) {

                newsModelList = newsModels;
                if(newsModelList!=null ){
                    if(newsModelList.size()>0){

                        Glide.with(getContext()).load(newsModelList.get(0).getImageUrl()).placeholder(R.drawable.logo).into(imageView);
                        textView.setText(newsModelList.get(0).getHeadline());
                    }

                }

            }
        });



        return view;
    }




    public  void GetAllNewsFromDataSource(String categoryId, String countryId, Context context)
    {
        handler = new Handler();

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


                    if(newsModelList!=null ){
                        if(newsModelList.size()>0){
                            listSize = newsModelList.size();

                            Glide.with(getContext()).load(newsModelList.get(0).getImageUrl()).placeholder(R.drawable.logo).into(imageView);
                            textView.setText(newsModelList.get(0).getHeadline());

                          for(;iterator<100;iterator++){
                              handler.postDelayed(new Runnable() {
                                  @Override
                                  public void run() {

                                      Glide.with(getContext()).load(newsModelList.get((counter) % listSize).getImageUrl()).placeholder(R.drawable.logo).into(imageView);
                                      textView.setText(newsModelList.get((counter) % listSize).getHeadline());
                                      counter++;

                                  }
                              },3000*(iterator));
                            }

                        }

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

    }

}
