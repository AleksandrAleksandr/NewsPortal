package com.example.newsportal.data.local

import com.example.newsportal.domain.model.Article
import com.example.newsportal.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDataSource(
    private val dao: NewsDao,
    private val mapper: ArticleDatabaseMapper
) : ILocalDataSource {

    override fun getAllNews(): Flow<ResultWrapper<List<Article>>> {
        return dao.getAll().map { databaseList ->
            ResultWrapper.Success(databaseList.map (mapper::mapToDomain) )
        }
    }

    override fun insertNews(news: List<Article>) {
        val mapped = news.map { mapper.mapToLocal(it) }
        dao.add(mapped)
    }
}