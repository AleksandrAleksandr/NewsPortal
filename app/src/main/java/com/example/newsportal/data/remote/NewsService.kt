package com.example.newsportal.data.remote

import com.example.newsportal.BuildConfig
import com.example.newsportal.data.remote.model.NewsResponce
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("top-headlines?country=ru&apiKey=${key}")
    suspend fun getTopNewsByCategory(@Query("category") category: String): Response<NewsResponce>

    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
        const val key = BuildConfig.API_KEY
    }
}