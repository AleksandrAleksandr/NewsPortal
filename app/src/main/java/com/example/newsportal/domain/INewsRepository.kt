package com.example.newsportal.domain

import com.example.newsportal.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface INewsRepository {
    fun getNewsList(): Flow<List<Article>>
}