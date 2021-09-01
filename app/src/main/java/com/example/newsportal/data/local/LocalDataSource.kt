package com.example.newsportal.data.local

import com.example.newsportal.domain.model.Article
import com.example.newsportal.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDataSource(
    private val newsDao: NewsDao,
    private val bookmarksDao: BookmarksDao,
    private val mapper: ArticleDatabaseMapper
) : ILocalDataSource {

    override fun getAllNews(): Flow<ResultWrapper<List<Article>>> {
        return newsDao.getAll().map { databaseList ->
            ResultWrapper.Success(databaseList.map (mapper::mapToDomain) )
        }
    }

    override fun insertNews(news: List<Article>) {
        val mapped = news.map { mapper.mapToLocal(it) }
        newsDao.add(mapped)
    }

    override fun addBookmark(article: Article) {
        val local = mapper.mapToLocal(article)
        newsDao.updateArticle(local)
        val bookmark = mapper.mapDomainToBookmark(article)
        bookmarksDao.addBookmark(bookmark)
    }

    override fun deleteBookmark(article: Article) {
        val local = mapper.mapToLocal(article)
        newsDao.updateArticle(local)
        val bookmark = mapper.mapDomainToBookmark(article)
        bookmarksDao.deleteBookmark(bookmark)
    }

    override fun getBookmarks(): Flow<List<Article>> {
        return bookmarksDao.getBookmarks().map { bookmarkList ->
            bookmarkList.map { mapper.mapBookmarkToDomain(it) }
        }
    }
}