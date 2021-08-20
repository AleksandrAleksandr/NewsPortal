package com.example.newsportal.data.remote

import com.example.newsportal.domain.model.Article
import com.example.newsportal.utils.ResultWrapper

class RemoteDataSource(
    private val service: NewsService,
    private val mapper: ArticleNetworkMapper
) : IRemoteDataSource, BaseApiResponce() {

//    override suspend fun getNewsByCategory(category: String): List<Article> {
//        return service
//            .getTopNewsByCategory(category)
//            .articles?.map { mapper.mapToDomain(it, category) }
//            .orEmpty()
//    }

    override suspend fun getNewsByCategory(category: String): ResultWrapper<List<Article>> {

        return safeApiCall { service.getTopNewsByCategory(category) }
            .map { mapper.mapResponceToDomainList(it, category) }

    }
}