package com.example.newsportal.data.remote

import com.example.newsportal.BuildConfig
import com.example.newsportal.data.remote.model.NewsResponce
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("top-headlines?country=${COUNTRY}&apiKey=${key}")
    suspend fun getTopNewsByCategory(@Query("category") category: String): Response<NewsResponce>

    @GET("everything?language=${LANGUAGE}&pageSize=${PAGE_SIZE}&apiKey=${key}")
    suspend fun getNewsBySearch(
        @Query("q") phrase: String,
        @Query("from") from: String,
        @Query("to") to: String
    ): Response<NewsResponce>

    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
        private const val key = BuildConfig.API_KEY
        private const val COUNTRY = "us"
        private const val LANGUAGE = "en"
        private const val PAGE_SIZE = "100"
    }
}