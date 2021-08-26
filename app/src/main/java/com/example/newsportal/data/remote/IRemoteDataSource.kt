package com.example.newsportal.data.remote

import com.example.newsportal.domain.model.Article
import com.example.newsportal.utils.ResultWrapper

interface IRemoteDataSource {

    suspend fun getNewsByCategory(category: String): ResultWrapper<List<Article>>

    suspend fun getNewsBySearch(phrase: String, from: String, to: String): ResultWrapper<List<Article>>
}