package com.example.demo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.demo.Model.UserDatabase;
import com.example.demo.R;

public class Updatepassword extends AppCompatActivity {
    String input_Email,input_Password;
    Button btn_set;
    EditText edt_current,edt_new_password,edt_confirm_pass;
    ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatepassword);
        controller();
        setClick();
    }
    private void controller(){
        input_Email=getIntent().getStringExtra("key_email");
        input_Password=getIntent().getStringExtra("key_password");
        edt_current =findViewById(R.id.current_pass);
        edt_new_password=findViewById(R.id.new_pass);
        img_back=findViewById(R.id.icon_back);
        edt_confirm_pass=findViewById(R.id.confirm_pass);
        btn_set=findViewById(R.id.btn_set);
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    private void setClick(){
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDatabase db = new UserDatabase(Updatepassword.this);
                String tmp_current =edt_current.getText().toString().trim();
                String tmp_newpass=edt_new_password.getText().toString().trim();
                String tmp_confirm=edt_confirm_pass.getText().toString().trim();
                if(tmp_current.isEmpty()||tmp_newpass.isEmpty()||tmp_confirm.isEmpty()){
                    Toast.makeText(Updatepassword.this, "Please fill all information", Toast.LENGTH_SHORT).show();
                }
                if(input_Password.compareTo(tmp_current)!=0){
                    Toast.makeText(Updatepassword.this, "Password current didn't match", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(tmp_newpass.compareTo(tmp_confirm)==0)
                    {
                        if(input_Password.compareTo(tmp_newpass)!=0)
                        {
                            if(db.isValid(tmp_newpass)){
                                boolean rs=db.UpdatePassword(input_Email,tmp_newpass);
                                if(rs){
                                    Toast.makeText(Updatepassword.this, "Success", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else {
                                    Toast.makeText(Updatepassword.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(Updatepassword.this, "Please fill contain at least 8 characters, having letter, digit and special character ", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(Updatepassword.this, "Password has existed", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(Updatepassword.this, "Password and Confirm password didn't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}