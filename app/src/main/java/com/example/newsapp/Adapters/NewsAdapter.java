package com.example.newsapp.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.newsapp.Interfaces.ItemListner;
import com.example.newsapp.Models.NewsModel;
import com.example.newsapp.R;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    List<NewsModel> newsModelList;
    ItemListner itemListner;
    Context context;

    public NewsAdapter(List<NewsModel> newsModelList, ItemListner itemListner,Context context) {
        this.newsModelList = newsModelList;
        this.itemListner = itemListner;
        this.context =context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_item,viewGroup,false);
        return new NewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        NewsModel newsModel = newsModelList.get(i);
        viewHolder.headline.setText(newsModel.getHeadline());
        viewHolder.body.setText(newsModel.getBodyLine()+"...");
        viewHolder.date.setText(newsModel.getDate());
        viewHolder.itemView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transition_animation));
        Glide.with(context).load(newsModel.getImageUrl()).placeholder(R.drawable.logo).into(viewHolder.image);

    }

    @Override
    public int getItemCount() {
        return newsModelList.size();
    }
    public void setNewList(List<NewsModel> newList){
        newsModelList = newList;
       notifyDataSetChanged();

    }
    public List<NewsModel> getList(){return newsModelList;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView headline,body,date,author,link;
        private ImageView image,share_btn;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            headline = itemView.findViewById(R.id.headline);
            body = itemView.findViewById(R.id.body);
            date = itemView.findViewById(R.id.date);
            image = itemView.findViewById(R.id.image);
            share_btn = itemView.findViewById(R.id.share_btn);



            share_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemListner.onClickShare(getAdapterPosition());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemListner.onClickItem(getAdapterPosition());
                }
            });

        }
    }
}
