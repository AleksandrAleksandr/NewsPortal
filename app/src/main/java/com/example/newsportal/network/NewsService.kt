package com.example.newsportal.network

import com.example.newsportal.model.NewsResponce
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    companion object{
        const val BASE_URL = "https://newsapi.org/v2/"
    }

    @GET("top-headlines?country=ru&apiKey=036be43f041b4a8e98f513cadd577f4f")
    suspend fun getTopNewsByCategory(@Query("category") category: String): NewsResponce
}