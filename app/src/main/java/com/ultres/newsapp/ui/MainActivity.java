package com.ultres.newsapp.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ultres.newsapp.R;
import com.ultres.newsapp.adapters.CategoryAdapter;
import com.ultres.newsapp.adapters.NewsAdapter;
import com.ultres.newsapp.databinding.ActivityMainBinding;
import com.ultres.newsapp.interfaces.INewsApis;
import com.ultres.newsapp.interfaces.RVClickListener;
import com.ultres.newsapp.model.CategoryModel;
import com.ultres.newsapp.network.NewsResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity{

    private NewsAdapter newsAdapter;
    ActivityMainBinding binding;
    INewsApis newsApis;
    List<CategoryModel>categoryList = new ArrayList<>();
    String country = "eg";
    String category = "general";

    // API = https://newsapi.org/v2/top-headlines?country=eg&category=sport&apiKey=fa72aea7f1af46a6a45be8aa23e21b64
    //baseUrl = https://newsapi.org/
    //endPoint = v2/top-headlines?country=eg&category=sport&apiKey=fa72aea7f1af46a6a45be8aa23e21b64



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        newsApis = retrofit.create(INewsApis.class);

        getDataFromApi(country ,category);

        categoryList.add(new CategoryModel("General"));
        categoryList.add(new CategoryModel("Business"));
        categoryList.add(new CategoryModel("Health"));
        categoryList.add(new CategoryModel("Science"));
        categoryList.add(new CategoryModel("Sport"));
        categoryList.add(new CategoryModel("Technology"));


        LinearLayoutManager layoutManager =
                new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        CategoryAdapter categoryAdapter= new CategoryAdapter(categoryList,rvClickListener);

        binding.categoryRV.setLayoutManager(layoutManager);
        binding.categoryRV.setAdapter(categoryAdapter);

        setSupportActionBar(binding.topAppBar);
    }

    MenuItem egItem,usItem,trItem;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filtering_menu,menu);
        egItem = menu.findItem(R.id.eg);
        usItem = menu.findItem(R.id.us);
        trItem = menu.findItem(R.id.tr);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.eg:
                country = "eg";
                getDataFromApi(country,category);
                egItem.setChecked(true);
                usItem.setChecked(false);
                trItem.setChecked(false);
                break;
            case R.id.us:
                country = "us";
                getDataFromApi(country ,category);
                egItem.setChecked(false);
                usItem.setChecked(true);
                trItem.setChecked(false);
                break;
            case R.id.tr:
                country = "tr";
                getDataFromApi(country ,category);
                egItem.setChecked(false);
                usItem.setChecked(false);
                trItem.setChecked(true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getDataFromApi(String country, String category) {
        newsApis.getNewsByQuires(country,category).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful() && response.body()!=null){
                    NewsResponse newsResponse = response.body();
                    newsAdapter = new NewsAdapter(newsResponse.getArticles(),rvClickListener);
                    binding.newsRecyclerView.setAdapter(newsAdapter);
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                String errorMassage = t.getLocalizedMessage();
                Toast.makeText(MainActivity.this,errorMassage,Toast.LENGTH_LONG).show();
            }
        });

    }

    RVClickListener rvClickListener = new RVClickListener() {
        @Override
        public void openNewsDetailsPages(Uri uri) {
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);
        }

        @Override
        public void categoryFiltration(String categoryName) {
            switch (categoryName){
                case "General":
                    category = "general";
                    getDataFromApi(country ,category);
                    binding.newsRecyclerView.getAdapter().getItemId(0);

                    break;
                case "Business":
                    category = "business";
                    getDataFromApi(country ,category);
                    break;
                case "Health":
                    category = "health";
                    getDataFromApi(country ,category);
                    break;

                case "Science":
                    category = "science";
                    getDataFromApi(country ,category);
                    break;

                case "Sports":
                    category = "sports";
                    getDataFromApi(country ,category);
                    break;

                case "Technology":
                    category = "technology";
                    getDataFromApi(country ,category);
                    break;
            }
        }
    };
}