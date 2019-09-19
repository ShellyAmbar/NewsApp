package com.example.newsapp;

import android.app.Activity;
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
import androidx.core.app.ComponentActivity;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.newsapp.Models.NewsModel;
import com.example.newsapp.R;

import java.util.zip.Inflater;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewsDataDialogFragment extends AppCompatActivity {
    private TextView one,two,tree,four,five;
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
        image =findViewById(R.id.icon_dialog);
        Button button = findViewById(R.id.button);
        Button button_link = findViewById(R.id.button_link);

        button_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(NewsDataDialogFragment.this, WebViewActivity.class);
                String url =getIntent().getStringExtra("url");
                intent1.putExtra("url",url);
                startActivity(intent1);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        Intent intent = getIntent();
        one.setText(intent.getStringExtra("one"));
        two.setText(intent.getStringExtra("two"));
        tree.setText(intent.getStringExtra("tree"));
        four.setText(intent.getStringExtra("four"));
        five.setText(intent.getStringExtra("six"));

        Glide.with(this).load(intent.getStringExtra("five")).placeholder(R.drawable.logo).into(image);


    }



}
