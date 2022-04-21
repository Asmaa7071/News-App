package com.ultres.newsapp.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ultres.newsapp.R;
import com.ultres.newsapp.databinding.ItemViewBinding;
import com.ultres.newsapp.interfaces.RVClickListener;
import com.ultres.newsapp.network.Article;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder>{
    List<Article> articleList;
    RVClickListener rvClickListener;

    public NewsAdapter(List<Article> articleList, RVClickListener rvClickListener) {
        this.articleList = articleList;
        this.rvClickListener = rvClickListener;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsHolder holder, int position) {
        holder.binding.setArticle(articleList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newsWebPage = articleList.get(holder.getAdapterPosition()).getUrl();
                Uri newsWebPageUri = Uri.parse(newsWebPage);
                rvClickListener.openNewsDetailsPages(newsWebPageUri);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    static class NewsHolder extends RecyclerView.ViewHolder{

        ItemViewBinding binding;
        public NewsHolder(ItemViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
