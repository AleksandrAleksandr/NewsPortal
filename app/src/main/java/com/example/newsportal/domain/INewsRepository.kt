package com.example.newsportal.domain

import com.example.newsportal.domain.model.Article
import com.example.newsportal.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface INewsRepository {

    fun getNewsList(): Flow<ResultWrapper<List<Article>>>

    fun getNewsBySearch(
        phrase: String,
        interval: Pair<String, String>
    ): Flow<ResultWrapper<List<Article>>>
}