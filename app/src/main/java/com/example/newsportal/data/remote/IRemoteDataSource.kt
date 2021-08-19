package com.example.newsportal.data.remote

import com.example.newsportal.domain.model.Article

interface IRemoteDataSource {

    suspend fun getNewsByCategory(category: String): List<Article>
}