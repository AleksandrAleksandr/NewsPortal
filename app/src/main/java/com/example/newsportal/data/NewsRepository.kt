package com.example.newsportal.data

import com.example.newsportal.data.local.ILocalDataSource
import com.example.newsportal.domain.categories.Categories
import com.example.newsportal.data.remote.IRemoteDataSource
import com.example.newsportal.domain.INewsRepository
import com.example.newsportal.domain.model.Article
import com.example.newsportal.utils.ResultWrapper
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
                val newsResult = remoteSource.getNewsByCategory(elem.name)
                newsResult.ifSuccess { localSource.insertNews(it) }
            }
        }
    }

    override fun getNewsList(): Flow<ResultWrapper<List<Article>>>  = flow {
        emit(ResultWrapper.Loading)
        refresh()
        emitAll(localSource.getAllNews())
    }.flowOn(Dispatchers.IO)

}