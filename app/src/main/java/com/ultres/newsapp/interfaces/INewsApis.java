package com.ultres.newsapp.interfaces;

import com.ultres.newsapp.network.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface INewsApis {

    @GET("v2/top-headlines?apiKey=fa72aea7f1af46a6a45be8aa23e21b64")
    Call<NewsResponse>getNewsByQuires(@Query("country")String country, @Query("category")String category);


}
