package com.example.newsportal.data.remote

import com.example.newsportal.BuildConfig
import com.example.newsportal.data.remote.model.NewsResponce
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("top-headlines?country=us&apiKey=${key}")
    suspend fun getTopNewsByCategory(@Query("category") category: String): Response<NewsResponce>

    @GET("everything?language=en&pageSize=100&apiKey=${key}")
    suspend fun getNewsBySearch(
        @Query("q") phrase: String,
        @Query("from") from: String,
        @Query("to") to: String
    ): Response<NewsResponce>

    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
        private const val key = BuildConfig.API_KEY
    }
}