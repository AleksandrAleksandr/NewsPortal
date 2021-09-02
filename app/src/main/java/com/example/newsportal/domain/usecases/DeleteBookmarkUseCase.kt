package com.example.newsportal.domain.usecases

import com.example.newsportal.domain.INewsRepository
import com.example.newsportal.domain.model.Article

class DeleteBookmarkUseCase(private val repository: INewsRepository) {

    suspend operator fun invoke(article: Article) {
        article.isBookmarked = false
        repository.deleteBookmark(article)
    }
}