package com.example.newsportal.data

import com.example.newsportal.data.local.ILocalDataSource
import com.example.newsportal.data.remote.IRemoteDataSource
import com.example.newsportal.domain.INewsRepository
import com.example.newsportal.domain.categories.Categories
import com.example.newsportal.domain.model.Article
import com.example.newsportal.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class NewsRepository(
    private val remoteSource: IRemoteDataSource,
    private val localSource: ILocalDataSource,
) : INewsRepository {

    private var firstUpdateHappened = false

    override suspend fun refresh() {
        withContext(Dispatchers.IO) {
            for (elem in Categories.values()) {
                val newsResult = remoteSource.getNewsByCategory(elem.name)
                newsResult.ifSuccess { localSource.insertNews(it) }
            }
        }
    }

    override fun getNewsList(): Flow<ResultWrapper<List<Article>>> = flow {
        emit(ResultWrapper.Loading)
        emit(localSource.getAllNewsOneTime())
        emit(ResultWrapper.Loading)
        if (!firstUpdateHappened){
            refresh()
            firstUpdateHappened = true
        }
        emitAll(localSource.getAllNews())
    }.flowOn(Dispatchers.IO)

    override fun getNewsBySearch(
        phrase: String,
        interval: Pair<String, String>
    ): Flow<ResultWrapper<List<Article>>> = flow {
        emit(ResultWrapper.Loading)
        val foundNews = remoteSource.getNewsBySearch(phrase, interval.first, interval.second)
        foundNews.ifSuccess { localSource.checkNewsIfBookmarked(it) }
        emit(foundNews)
    }.flowOn(Dispatchers.IO)

    override suspend fun addBookmark(article: Article) {
        withContext(Dispatchers.IO){
            localSource.addBookmark(article)
        }
    }

    override suspend fun deleteBookmark(article: Article) {
        withContext(Dispatchers.IO) {
            localSource.deleteBookmark(article)
        }
    }

    override fun getBookmarks(): Flow<List<Article>> = flow {
        emitAll(localSource.getBookmarks())
    }.flowOn(Dispatchers.IO)
}