package com.example.demo.Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.Model.Question;
import com.example.demo.R;
import com.example.demo.activities.ScoreActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    private TextView question, process;
    private LinearLayout option_container;
    private Button shareBtn, nextBtn;

    private Toolbar toolbar;

    private int count = 0;

    private  List<Question> list;

    private int position = 0;

    private int score = 0 ;

    private String category;

    private int setNo;

    private Dialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        addControl();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
    }

    private void addControl(){
        toolbar = findViewById(R.id.toolbarQuestion);
        question = findViewById(R.id.question);
        process = findViewById(R.id.textViewProcess);
        option_container = findViewById(R.id.option_layout);
        shareBtn = findViewById(R.id.shareBtn);
        nextBtn = findViewById(R.id.nextBtn);
        category = getIntent().getStringExtra("category");
        setNo = getIntent().getIntExtra("setNo",1);
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading);
        //loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.round_txt));
        //loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);

        list = new ArrayList<>();
        loadingDialog.show();
        myRef.child("SETS").child(category).child("questions").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    list.add(snapshot1.getValue(Question.class));
                }
                if(list.size()>0){
                    for (int i = 0 ;i<4;i++){
                        option_container.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checkAnswer((Button) v);
                            }
                        });
                    }
                    playAnim(question,0,list.get(position).getQuestion());
                    nextBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            nextBtn.setEnabled(false);
                            nextBtn.setAlpha(0.7f);
                            enableOption(true);
                            position++;
                            if(position == list.size()){
                                Intent scoreIntent = new Intent(QuestionActivity.this, ScoreActivity.class);
                                scoreIntent.putExtra("score",score);
                                scoreIntent.putExtra("total",list.size());
                                startActivity(scoreIntent);
                                finish();
                                return;
                            }
                            count = 0;
                            playAnim(question,0,list.get(position).getQuestion());
                        }
                    });
                }
                else {
                    finish();
                    Toast.makeText(QuestionActivity.this,"no question",Toast.LENGTH_SHORT).show();
                }
                loadingDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(QuestionActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
                finish();
            }
        });
    }

    private void playAnim(View view, int value, String data){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animation) {
                        if(value == 0 && count < 4){
                            String option = "";
                            if(count == 0){
                                option = list.get(position).getOption1();
                            }
                            else  if(count == 1){
                                option = list.get(position).getOption2();
                            }
                            else  if(count == 2){
                                option = list.get(position).getOption3();
                            }
                            else  if(count == 3){
                                option = list.get(position).getOption4();
                            }
                           playAnim(option_container.getChildAt(count),0, option);
                            count++;
                        }
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if(value == 0 ){
                            try {
                                ((TextView)view).setText(data);
                                process.setText(position+1+"/"+list.size());
                            }catch (ClassCastException ex){
                                ((Button)view).setText(data);
                            }
                            view.setTag(data);
                            playAnim(view,1,data);
                        }
                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animation) {

                    }
                });
    }

    private void checkAnswer(Button selectOption){
        enableOption(false);
        nextBtn.setEnabled(true);
        nextBtn.setAlpha(1);
        if(selectOption.getText().toString().equals(list.get(position).getAnswer())){
            score++;
            selectOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
        }
        else {
            selectOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
            Button correctOption = (Button) option_container.findViewWithTag(list.get(position).getAnswer());
            correctOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
        }
    }

    private void enableOption(boolean enable){
        for (int i = 0 ;i<4;i++){
            option_container.getChildAt(i).setEnabled(enable);
            if(enable){
                option_container.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E0B0FF")));
        }
    }
}
}