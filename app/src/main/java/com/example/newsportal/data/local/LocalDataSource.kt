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

    override fun getAllNewsOneTime(): ResultWrapper<List<Article>> {
        return ResultWrapper.Success(newsDao.getAllOneTime().map { mapper.mapToDomain(it) })
    }

    override fun insertNews(news: List<Article>) {
        val mapped = news.map { mapper.mapToLocal(it) }
        newsDao.add(mapped)
    }

    override fun addBookmark(article: Article) {
        newsDao.updateArticle(mapper.mapToLocal(article))
        bookmarksDao.addBookmark(mapper.mapDomainToBookmark(article))
    }

    override fun deleteBookmark(article: Article) {
        newsDao.updateArticle(mapper.mapToLocal(article))
        bookmarksDao.deleteBookmark(mapper.mapDomainToBookmark(article))
    }

    override fun getBookmarks(): Flow<List<Article>> {
        return bookmarksDao.getBookmarks().map { bookmarkList ->
            bookmarkList.map { mapper.mapBookmarkToDomain(it) }
        }
    }

    override fun checkNewsIfBookmarked(news: List<Article>) {
        news.forEach {
            if (bookmarksDao.findBookmark(it.title) != 0)
                it.isBookmarked = true
        }
    }
}