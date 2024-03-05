package com.example.demo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.demo.R;
import com.google.android.material.textfield.TextInputEditText;

public class ForgotPassword extends AppCompatActivity {

    Button btnBack, btnReceive;
    TextInputEditText txtReceive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        addControl();
        setClick();
    }

    private void addControl(){
        btnBack = findViewById(R.id.buttonBackForgot);
        btnReceive = findViewById(R.id.btn_receive);
        txtReceive = findViewById(R.id.txtReceive);
    }
    private void setClick(){
        btnReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable icError = getResources().getDrawable(R.drawable.ic_error);
                icError.setBounds(0,0,icError.getIntrinsicWidth(), icError.getIntrinsicHeight());
                String receive = txtReceive.getText().toString().trim();
                if(receive.isEmpty()){
                    txtReceive.setCompoundDrawables(null,null,icError,null);
                    txtReceive.setError("Please add email or phone number");
                }
                if(!receive.isEmpty()){
                    txtReceive.setCompoundDrawables(null,null,null,null);
                    Intent intent = new Intent(ForgotPassword.this, home_page.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPassword.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}