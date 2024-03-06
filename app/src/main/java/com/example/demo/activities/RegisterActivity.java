package com.example.demo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.demo.Model.UserDatabase;
import com.example.demo.R;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {
    Button btn_Confirm, btn_back;
    TextInputEditText txtName, txtEmail, txtPass, txtConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        addControl();
        setClick();
    }

    private void addControl(){
        btn_Confirm = findViewById(R.id.btn_Register_Confirm);
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPass = findViewById(R.id.txtPass);
        txtConfirm = findViewById(R.id.txtConfirm);
        btn_back = findViewById(R.id.btn_back);
    }
    private void setClick() {
        btn_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable icError = getResources().getDrawable(R.drawable.ic_error);
                icError.setBounds(0,0,icError.getIntrinsicWidth(), icError.getIntrinsicHeight());
                String name = txtName.getText().toString().trim();
                String email = txtEmail.getText().toString().trim();
                String pass = txtPass.getText().toString().trim();
                String confirm = txtConfirm.getText().toString().trim();

                if(name.isEmpty()){
                    txtName.setCompoundDrawables(null,null,icError,null);
                    txtName.setError("Please add name");
                }
                if(email.isEmpty()){
                    txtEmail.setCompoundDrawables(null,null,icError,null);
                    txtEmail.setError("Please add email");
                }
                if(pass.isEmpty()){
                    txtPass.setCompoundDrawables(null,null,icError,null);
                    txtPass.setError("Please add password");
                }
                if(confirm.compareTo(pass) != 0){
                    txtConfirm.setCompoundDrawables(null,null,icError,null);
                    txtConfirm.setError("Confirm password not match with your password");
                }
                if(!pass.isEmpty() && !name.isEmpty() && !email.isEmpty() && !(confirm.compareTo(pass) != 0)){
                    txtName.setCompoundDrawables(null,null,null,null);
                    txtPass.setCompoundDrawables(null,null,null,null);
                    txtEmail.setCompoundDrawables(null,null,null,null);
                    txtConfirm.setCompoundDrawables(null,null,null,null);
                    Intent intent = new Intent(RegisterActivity.this, home_page.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btn_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addUser() {
        String userName = txtName.getText().toString().trim();
        String email= txtEmail.getText().toString().trim();
        String passWord =txtPass.getText().toString().trim();
        String confirmPassword= txtConfirm.getText().toString().trim();
        UserDatabase db = new UserDatabase(this);
        if(userName.length()==0||email.length()==0||passWord.length()==0||confirmPassword.length()==0) {
            Toast.makeText(this, "Please input information", Toast.LENGTH_SHORT).show();
            return;
        }
        if(passWord.compareTo(confirmPassword)==0) {
            if(db.isValid(passWord)){
                if(!db.CheckEmailExists(email)) {
                    db.register(userName, email, passWord);
                    Toast.makeText(this, "Record Inserted ", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(this, "Email existed", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "Please fill contain at least 8 characters, having letter, digit and special character ", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Password and Confirm password didn't match", Toast.LENGTH_SHORT).show();
        }
    }
//    public  static boolean isValid(String pass){
//        int f1=0,f2=0,f3=0;
//        if(pass.length()<0){
//            return false;
//        }
//        else{
//            for (int i =0;i<pass.length();i++){
//                if(Character.isLetter(pass.charAt(i)))
//                    f1=1;
//            }
//            for (int h =0;h<pass.length();h++){
//                if(Character.isDigit(pass.charAt(h)));
//                f2=1;
//            }
//            for (int k=0;k<pass.length();k++)
//            {
//                char c = pass.charAt(k);
//                if(c>=33&&c<=46||c==64){
//                    f3=1;
//                }
//            }
//            if(f1==1&&f2==1&&f3==1)
//                return true;
//            return false;
//        }
//    }

}