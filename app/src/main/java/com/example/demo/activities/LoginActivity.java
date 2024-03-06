package com.example.demo.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demo.Model.UserDatabase;

import android.graphics.drawable.Drawable;
import android.widget.Button;

import com.example.demo.R;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    EditText user_Name,passWord;
    TextInputEditText txtName, txtPass;
    Button btnLogin, btnCreate, btnForgot, btnFB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user_Name =(EditText) findViewById(R.id.editName);
        passWord=(EditText) findViewById(R.id.editPass);
    }

    public void Create(View view) {
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    public void clickToLogin(View view) {

        String email = user_Name.getText().toString();
        String edt_Password=passWord.getText().toString();
        UserDatabase db= new UserDatabase(this);
        if(email.length()==0||edt_Password.length()==0){
            Toast.makeText(this, "Please fill all information", Toast.LENGTH_SHORT).show();
        }
        else{
            if(db.login(email,edt_Password)==1)
            {
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, home_page.class);
                intent.putExtra("key_email",email);
                intent.putExtra("key_password",edt_Password);
                startActivity(intent);
            }
            else {
                Toast.makeText(this, "Invalid UserName and Password", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void forgotPassword(View view){
        Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
        startActivity(intent);
        addControl();
        onClick();
    }

    public void addControl(){
        txtName = findViewById(R.id.editName);
        txtPass = findViewById(R.id.editPass);
        btnLogin = findViewById(R.id.button_Login);
        btnCreate = findViewById(R.id.btnCreate);
        btnForgot = findViewById(R.id.btnForgot);
        btnFB = findViewById(R.id.q1);
    }

    private void onClick(){
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Drawable icError = getResources().getDrawable(R.drawable.ic_error);
//                icError.setBounds(0,0,icError.getIntrinsicWidth(), icError.getIntrinsicHeight());
//                String name = txtName.getText().toString().trim();
//                String pass = txtPass.getText().toString().trim();
//                if(name.isEmpty()){
//                    txtName.setCompoundDrawables(null,null,icError,null);
//                    txtName.setError("Please add name");
//                }
//                if(pass.isEmpty()){
//                    txtPass.setCompoundDrawables(null,null,icError,null);
//                    txtPass.setError("Please add password");
//                }
//                if(!pass.isEmpty() && !name.isEmpty()){
//                    txtPass.setCompoundDrawables(null,null,null,null);
//                    txtPass.setCompoundDrawables(null,null,null,null);
//                    Intent intent = new Intent(LoginActivity.this, home_page.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//        });

        btnFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, home_page.class);
                startActivity(intent);
                finish();
            }
        });
        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });
    }

}