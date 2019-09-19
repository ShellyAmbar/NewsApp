package com.example.newsapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.newsapp.Models.NewsModel;
import com.example.newsapp.Models.WeatherModel;

import de.hdodenhof.circleimageview.CircleImageView;

public class WeatherDataDialogFragment extends AppCompatActivity {
    private TextView one,two,tree,four,five,six;
    private CircleImageView image;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.costum_layout_dialog);

        one = findViewById(R.id.one);
        two =findViewById(R.id.two);
        tree = findViewById(R.id.tree);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        image =findViewById(R.id.icon_dialog);
        Button button = findViewById(R.id.button);

        Button button_link = findViewById(R.id.button_link);

        button_link.setVisibility(View.GONE);

        Intent intent = getIntent();
        one.setText(intent.getStringExtra("one"));
        two.setText(intent.getStringExtra("two"));
        tree.setText(intent.getStringExtra("tree"));
        four.setText(intent.getStringExtra("four"));
        five.setText(intent.getStringExtra("five"));
        six.setText(intent.getStringExtra("six"));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        Glide.with(this).load(intent.getStringExtra("seven")).placeholder(R.drawable.weather).into(image);



    }


}
