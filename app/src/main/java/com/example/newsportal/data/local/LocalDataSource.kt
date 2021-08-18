package com.example.newsportal.data.local

import com.example.newsportal.data.local.model.ArticleLocal
import com.example.newsportal.domain.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDataSource(
    private val dao: NewsDao,
    private val mapper: DatabaseMapper
) : ILocalDataSource {

    override fun getAllNews(): Flow<List<Article>> {
        return dao.findAll().map { databaseList ->
            databaseList.map { mapper.mapToDomain(it) }
        }
    }

    override fun insertNews(news: List<Article>) {
        val mapped = news.map { mapper.mapToLocal(it) }
        dao.add(mapped)
    }
}