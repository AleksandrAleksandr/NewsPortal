package com.example.newsportal.data

import com.example.newsportal.domain.categories.Categories
import com.example.newsportal.data.local.NewsDao
import com.example.newsportal.data.remote.NewsService
import com.example.newsportal.data.local.model.ArticleLocal

class NewsRepository(private val mService: NewsService, private val newsDao: NewsDao) {

    val data = newsDao.findAll()

    suspend fun refresh() {
        for (elem in Categories.values()) {
            val news = mService.getTopNewsByCategory(elem.name).articles?.map {
                ArticleLocal(
                    category = elem.name,
                    author = it.author,
                    title = it.title,
                    description = it.description,
                    url = it.url,
                    urlToImage = it.urlToImage,
                    publishedAt = it.publishedAt,
                    content = it.content
                )
            }
            news?.let { newsDao.add(it) }
        }
    }
}