package com.example.newsportal.data.remote

import com.example.newsportal.domain.model.Article
import com.example.newsportal.utils.ResultWrapper

interface IRemoteDataSource {

    suspend fun getNewsByCategory(category: String): ResultWrapper<List<Article>>
}