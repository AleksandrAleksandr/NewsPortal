package com.example.newsportal.repository

import com.example.newsportal.categories.Categories
import com.example.newsportal.model.ArticleLocal
import com.example.newsportal.network.NewsService
import com.example.newsportal.room.NewsDao

class NewsRepository(private val mService: NewsService, private val newsDao: NewsDao) {

    val data = newsDao.findAll()

    suspend fun refresh() {
        for (elem in Categories.values()){
            val news = mService.getTopNewsByCategory(elem.name).articles.map {
                ArticleLocal(
                    category= elem.name,
                    author = it.author,
                    title = it.title,
                    description = it.description,
                    url = it.url,
                    urlToImage = it.urlToImage,
                    publishedAt = it.publishedAt,
                    content = it.content)
            }
            newsDao.add(news)
        }
    }
}