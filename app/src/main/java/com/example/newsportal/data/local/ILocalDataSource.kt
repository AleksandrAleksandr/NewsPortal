package com.example.newsportal.data.local

import com.example.newsportal.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface ILocalDataSource {
    fun getAllNews(): Flow<List<Article>>

    fun insertNews(news: List<Article>)
}