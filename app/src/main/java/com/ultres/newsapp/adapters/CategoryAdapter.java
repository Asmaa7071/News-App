package com.ultres.newsapp.adapters;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ultres.newsapp.R;
import com.ultres.newsapp.databinding.CategoryItemBinding;
import com.ultres.newsapp.interfaces.RVClickListener;
import com.ultres.newsapp.model.CategoryModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder>{

    int selectedItemPosition;

    List<CategoryModel>categoryList;
    RVClickListener rvClickListener;

    public CategoryAdapter(List<CategoryModel> categoryList,RVClickListener rvClickListener) {
        this.categoryList = categoryList;
        this.rvClickListener = rvClickListener;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.category_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryHolder holder, int position) {
        holder.binding.setCategory(categoryList.get(position));

        holder.binding.btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               selectedItemPosition = holder.getAdapterPosition();
               Log.i("TAG", "onClick: "+ selectedItemPosition);
               rvClickListener.categoryFiltration(categoryList.get(holder.getAdapterPosition()).getCategoryName());
               notifyDataSetChanged();
           }
       });
        for (int i =0; i<=position;i++){
            if (selectedItemPosition == position){
                holder.binding.btn.setBackgroundColor(Color.parseColor("#F9AA33"));
            }
            else
                holder.binding.btn.setBackgroundColor(Color.parseColor("#344955"));
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder{

        CategoryItemBinding binding;
        public CategoryHolder(CategoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
