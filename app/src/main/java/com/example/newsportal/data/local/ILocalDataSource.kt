package com.example.newsportal.data.local

import com.example.newsportal.domain.model.Article
import com.example.newsportal.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface ILocalDataSource {
    fun getAllNews(): Flow<ResultWrapper<List<Article>>>

    fun insertNews(news: List<Article>)

    fun addBookmark(article: Article)

    fun getBookmarks(): List<Article>
}