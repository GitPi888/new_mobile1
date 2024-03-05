package com.example.demo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.demo.Fragment.HomeFragment;
import com.example.demo.Fragment.HomeWork;
import com.example.demo.Fragment.UserFragment;
import com.example.demo.R;
import com.example.demo.databinding.ActivityHomePageBinding;

public class home_page extends AppCompatActivity {

    String email,password;
    ActivityHomePageBinding homeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(homeBinding.getRoot());
        replaceFragment(new HomeFragment());
        homeBinding.menuBottom.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.fragment_user) {
                addFragment(new UserFragment());
            } else if (item.getItemId() == R.id.fragment_Homework) {
                addFragment(new HomeWork());
            } else {
                addFragment(new HomeFragment());
            }
            return true;
        });
        email=getIntent().getStringExtra("key_email");
        password=getIntent().getStringExtra("key_password");
    }


    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ftc = fm.beginTransaction();
        ftc.replace(R.id.bottom_FrameLayout, fragment);
        ftc.commit();
    }

    public void addFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putString("email",email);
        bundle.putString("pass",password);
        fragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ftc = fm.beginTransaction();
        ftc.replace(R.id.bottom_FrameLayout, fragment);
        ftc.addToBackStack(fragment.getClass().getSimpleName());
        ftc.commit();
    }

}