package com.example.demo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.demo.R;

public class ScoreActivity extends AppCompatActivity {

    private TextView score, total;
    private Button doneBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        score = findViewById(R.id.score);
        total = findViewById(R.id.total_score);
        doneBtn = findViewById(R.id.doneBtn);

        score.setText(String.valueOf(getIntent().getIntExtra("score",0)));
        total.setText("OUT OF "+ String.valueOf(getIntent().getIntExtra("total",0)));
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}