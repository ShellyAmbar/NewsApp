package com.example.newsapp.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.AttrRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.newsapp.Adapters.NewsAdapter;
import com.example.newsapp.CommunicationManager;
import com.example.newsapp.Interfaces.ItemListner;
import com.example.newsapp.Interfaces.NotificationsListener;
import com.example.newsapp.Models.NewsModel;
import com.example.newsapp.Models.NewsModelProp.Source;
import com.example.newsapp.NewsDataDialogFragment;
import com.example.newsapp.R;
import com.example.newsapp.ViewModels.NewsViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.http.HTTP;

import static android.content.Context.MODE_PRIVATE;


public class NewsFragment extends Fragment implements ItemListner, NotificationsListener {
   private RecyclerView recyclerView;
   private List<NewsModel> newsModelList;
   private NewsAdapter newsAdapter;
   private static final String Category ="category";
   private SharedPreferences sharedPreferences;
   private NewsViewModel viewModel;
   String category;
   String Language;

   public NewsFragment(){}



   public static NewsFragment NewInstance(String category){
       Bundle bundle = new Bundle();
       bundle.putString(Category,category);
       NewsFragment newsFragment = new NewsFragment();
       newsFragment.setArguments(bundle);
       return newsFragment;
   }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_news, container, false);
        viewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        category = getArguments().getString(Category);
        Language = sharedPreferences.getString("CountryCode","us");

        recyclerView = view.findViewById(R.id.recycler);

        CommunicationManager communicationManager = new CommunicationManager();

        newsModelList = new ArrayList<>();
        newsAdapter = new NewsAdapter(newsModelList,this,getContext());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(newsAdapter);
        //GetAllNewsFromDataSource(category,Language,getContext());
        viewModel.getData(category,Language).observe(this,new Observer<List<NewsModel>>() {
            @Override
            public void onChanged(List<NewsModel> newsModels) {
                newsModelList = newsModels;
                newsAdapter.notifyDataSetChanged();

            }
        });

        //get list from viewmodel
      //  newsModelList =(List<NewsModel>) viewModel.getData(category,Language);
       // newsAdapter.notifyDataSetChanged();

        //observe on the data source



        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        Language = sharedPreferences.getString("CountryCode","us");
        GetAllNewsFromDataSource(category,Language,getContext());
    }




    @Override
    public void onClickItem(int pos) {
        ShowDialog(newsModelList.get(pos));
    }

    @Override
    public void onClickShare(int pos) {

        composeMmsMessage(newsModelList.get(pos).getHeadline(),null);

    }
    public void composeMmsMessage(String message, Uri attachment) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra("subject",message);
        intent.putExtra("sms_body", message);
        intent.putExtra(Intent.EXTRA_TEXT,message);
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public  void GetAllNewsFromDataSource(String categoryId, String countryId, Context context)
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
                      //  newsAdapter.notifyDataSetChanged();

                    }

                    newsAdapter.setNewList(newsModelList);
                    //newsAdapter.notifyDataSetChanged();



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

    public void ShowDialog(NewsModel newsModel){

        Intent intent = new Intent(getActivity(),NewsDataDialogFragment.class);
        intent.putExtra("one",newsModel.getHeadline());
        intent.putExtra("two",newsModel.getAuthor());
        intent.putExtra("tree",newsModel.getBodyLine());
        intent.putExtra("four",newsModel.getDate());
        intent.putExtra("five",newsModel.getImageUrl());
        intent.putExtra("six",newsModel.getSource().getSourceName());
        intent.putExtra("url",newsModel.getLink());
        getContext().startActivity(intent);

    }


    @Override
    public NewsModel getLastNews() {

        return newsAdapter.getList().get(0);
    }
}
