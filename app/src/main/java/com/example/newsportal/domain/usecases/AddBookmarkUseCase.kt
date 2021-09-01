package com.example.newsportal.domain.usecases

import com.example.newsportal.domain.INewsRepository
import com.example.newsportal.domain.model.Article

class AddBookmarkUseCase(private val repository: INewsRepository) {

    suspend operator fun invoke(article: Article) {
        repository.addBookmark(article)
    }
}