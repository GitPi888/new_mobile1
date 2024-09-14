package com.example.demo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.example.demo.R;


public class MainActivity extends AppCompatActivity {
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);
        WebView wb= findViewById(R.id.web_view);
        String url = "<iframe src=\"https://giphy.com/embed/buvA4PKyYdzv899UkV\" width=\"100%\" height=\"100%\" style=\"\" frameBorder=\"0\" class=\"giphy-embed\" allowFullScreen></iframe>";
        wb.loadData(url,"text/html","utf-8");
        wb.getSettings().setJavaScriptEnabled(true);
        wb.setWebChromeClient(new WebChromeClient());
//        Glide.with(this).load(R.drawable.loading).into(loading);
    }


}
