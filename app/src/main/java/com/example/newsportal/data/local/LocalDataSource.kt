package com.example.newsportal.data.local

import com.example.newsportal.domain.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDataSource(
    private val dao: NewsDao,
    private val mapper: ArticleDatabaseMapper
) : ILocalDataSource {

    override fun getAllNews(): Flow<List<Article>> {
        return dao.getAll().map { databaseList ->
            databaseList.map{mapper.mapToDomain(it)}
        }
    }

    override fun insertNews(news: List<Article>) {
        val mapped = news.map { mapper.mapToLocal(it) }
        dao.add(mapped)
    }
}