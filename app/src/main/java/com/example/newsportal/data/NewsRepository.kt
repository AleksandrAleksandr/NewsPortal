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
                    author = it.author.orEmpty(),
                    title = it.title.orEmpty(),
                    description = it.description.orEmpty(),
                    url = it.url.orEmpty(),
                    urlToImage = it.urlToImage.orEmpty(),
                    publishedAt = it.publishedAt.orEmpty(),
                    content = it.content.orEmpty()
                )
            }
            news?.let { newsDao.add(it) }
        }
    }
}