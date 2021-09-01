package com.example.newsportal.data.local

import com.example.newsportal.data.local.model.ArticleBookmark
import com.example.newsportal.data.local.model.ArticleLocal
import com.example.newsportal.domain.model.Article

class ArticleDatabaseMapper {

    fun mapToDomain(localArticle: ArticleLocal) = with(localArticle) {
        Article(
            category = category,
            author = author,
            title = title,
            description = description,
            url = url,
            urlToImage = urlToImage,
            publishedAt = publishedAt,
            content = content
        )
    }


    fun mapToLocal(article: Article) = with(article) {
        ArticleLocal(
            category = category,
            author = author,
            title = title,
            description = description,
            url = url,
            urlToImage = urlToImage,
            publishedAt = publishedAt,
            content = content
        )
    }

    fun mapDomainToBookmark(article: Article) = with(article) {
        ArticleBookmark(
            category = category,
            author = author,
            title = title,
            description = description,
            url = url,
            urlToImage = urlToImage,
            publishedAt = publishedAt,
            content = content
        )
    }

    fun mapBookmarkToDomain(bookmark: ArticleBookmark) = with(bookmark) {
        Article(
            category = category,
            author = author,
            title = title,
            description = description,
            url = url,
            urlToImage = urlToImage,
            publishedAt = publishedAt,
            content = content
        )
    }
}