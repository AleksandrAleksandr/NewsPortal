package com.example.newsportal.domain.usecases

import com.example.newsportal.domain.INewsRepository
import com.example.newsportal.domain.model.Article
import com.example.newsportal.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

class GetNewsUseCase(private val repository: INewsRepository) {

    suspend operator fun invoke(): Flow<ResultWrapper<List<Article>>> {
        return repository.getNewsList()
    }
}