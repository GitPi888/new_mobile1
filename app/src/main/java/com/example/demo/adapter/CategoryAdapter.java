package com.example.demo.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demo.Model.Category;
import com.example.demo.R;
import com.example.demo.activities.CategoryDetails;
import com.example.demo.clickItem;

import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewholder> {

    private List<Category> categoryList;
    private clickItem clickItem;

    public CategoryAdapter(List<Category> categoryList, clickItem clickItem) {
        this.categoryList = categoryList;
        this.clickItem = clickItem;
    }

    @NonNull
    @Override
    public CategoryViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View categoryView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_items, parent, false);
        return new CategoryViewholder(categoryView);

    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewholder holder, int position) {
        holder.setData(categoryList.get(position).getUrl(), categoryList.get(position).getName(),categoryList.get(position).getSets());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryViewholder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name;
        TextView description;

        public CategoryViewholder(@NonNull View itemView) {
            super(itemView);
            imageView =itemView.findViewById(R.id.category_image);
            name = itemView.findViewById(R.id.tenSp);
            description= itemView.findViewById(R.id.sets);

        }

        private void setData(String url, String name,final int sets){
            Glide.with(itemView.getContext()).load(url).into(imageView);
            this.name.setText(name);
            itemView.setOnClickListener((v -> {
                Intent setIntent = new Intent(itemView.getContext(), CategoryDetails.class);
                setIntent.putExtra("title",name);
                setIntent.putExtra("sets",sets);
                itemView.getContext().startActivity(setIntent);
            }));
        }

    }
}
