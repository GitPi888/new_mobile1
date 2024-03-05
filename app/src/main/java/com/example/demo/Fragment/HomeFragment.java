package com.example.demo.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.demo.Model.Category;
import com.example.demo.R;
import com.example.demo.activities.CategoryDetails;
import com.example.demo.adapter.CategoryAdapter;
import com.example.demo.clickItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    private List<Category> categoryList;

    private RecyclerView homeRecyclerView;
    private CategoryAdapter categoryAdapter;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        categoryList = generateCategory();
        homeRecyclerView = rootView.findViewById(R.id.categoryList);
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoryAdapter = new CategoryAdapter(categoryList, new clickItem() {
            @Override
            public void onClickCategory(Category category) {
                onClickToGoDetails(category);
            }

        });
        homeRecyclerView.setAdapter(categoryAdapter);

        myRef.child("Category").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                    categoryList.add(dataSnapshot1.getValue(Category.class));
                }
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),error.getMessage(), Toast.LENGTH_SHORT);
            }
        });
        return rootView;
    }

    private void onClickToGoDetails(Category category) {
        Intent intent = new Intent(getActivity(), CategoryDetails.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_category",category);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    private List<Category> generateCategory(){
        categoryList = new ArrayList<>();
        return categoryList;
    }
}