package com.example.newsportal.data.local

import com.example.newsportal.data.local.model.ArticleLocal
import com.example.newsportal.domain.model.Article

class DatabaseMapper {

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
}