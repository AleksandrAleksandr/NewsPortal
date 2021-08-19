package com.example.newsportal.data

import com.example.newsportal.data.local.ILocalDataSource
import com.example.newsportal.domain.categories.Categories
import com.example.newsportal.data.remote.IRemoteDataSource
import com.example.newsportal.domain.INewsRepository
import com.example.newsportal.domain.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class NewsRepository(
    private val remoteSource: IRemoteDataSource,
    private val localSource: ILocalDataSource,
) : INewsRepository {

    private suspend fun refresh() {
        withContext(Dispatchers.IO){
            for (elem in Categories.values()) {
                val news = remoteSource.getNewsByCategory(elem.name)
                localSource.insertNews(news)
            }
        }
    }

    override fun getNewsList(): Flow<List<Article>>  = flow {
        emitAll(localSource.getAllNews())
        refresh()
    }.flowOn(Dispatchers.IO)

}