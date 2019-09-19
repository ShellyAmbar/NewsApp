package com.example.newsapp.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.Interfaces.ItemListner;
import com.example.newsapp.Models.NewsModel;
import com.example.newsapp.Models.WeatherModel;
import com.example.newsapp.NewsDataDialogFragment;
import com.example.newsapp.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    private List<WeatherModel> weatherModels;
    private Context context;
    private ItemListner itemListner;

    public WeatherAdapter(List<WeatherModel> weatherModels, Context context,ItemListner itemListner) {
        this.weatherModels = weatherModels;
        this.context = context;
        this.itemListner = itemListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.weather_item,parent,false);
        return new WeatherAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        WeatherModel weatherModel = weatherModels.get(position);
        holder.temp.setText(weatherModel.getMain().getTemp());
        holder.date.setText(weatherModel.getDate().trim());
        Glide.with(context).load(weatherModel.getWeather().getIcon()).placeholder(R.drawable.weather).into(holder.icon);

    }

    @Override
    public int getItemCount() {
        return weatherModels.size();
    }
    public void setNewList(List<WeatherModel> weatherModels){
        this.weatherModels = weatherModels;
        notifyDataSetChanged();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView icon;
        private TextView date,temp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            date = itemView.findViewById(R.id.date);
            temp = itemView.findViewById(R.id.temp);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    itemListner.onClickItem(getAdapterPosition());

                }
            });

        }
    }
}
