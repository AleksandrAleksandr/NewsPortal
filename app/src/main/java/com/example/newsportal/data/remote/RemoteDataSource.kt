package com.example.newsportal.data.remote

import com.example.newsportal.data.remote.model.ArticleNetwork
import com.example.newsportal.domain.model.Article

class RemoteDataSource(
    private val service: NewsService,
    private val mapper: NetworkMapper
) : IRemoteDataSource {

    override suspend fun getNewsByCategory(category: String): List<Article> {
        return service
            .getTopNewsByCategory(category)
            .articles?.map { mapper.mapToDomain(it, category) }
            .orEmpty()
    }

}