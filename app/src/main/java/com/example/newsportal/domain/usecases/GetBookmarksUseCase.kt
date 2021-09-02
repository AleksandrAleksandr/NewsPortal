package com.example.newsportal.domain.usecases

import com.example.newsportal.domain.INewsRepository
import com.example.newsportal.domain.model.Article
import kotlinx.coroutines.flow.Flow

class GetBookmarksUseCase(private val repository: INewsRepository) {

    suspend operator fun invoke(): Flow<List<Article>> {
        return repository.getBookmarks()
    }
}