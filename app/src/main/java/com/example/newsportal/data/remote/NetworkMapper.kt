package com.example.newsportal.data.remote

import com.example.newsportal.data.remote.model.ArticleNetwork
import com.example.newsportal.domain.model.Article

class NetworkMapper {

    fun mapToDomain(articleNetwork: ArticleNetwork, category: String) = with(articleNetwork) {
        Article(
            category = category,
            author = author.orEmpty(),
            title = title.orEmpty(),
            description = description.orEmpty(),
            url = url.orEmpty(),
            urlToImage = urlToImage.orEmpty(),
            publishedAt = publishedAt.orEmpty(),
            content = content.orEmpty()
        )
    }
}