package com.example.newsportal.domain.usecases

import com.example.newsportal.domain.INewsRepository

class RefreshNewsUseCase(private val repository: INewsRepository) {

    suspend operator fun invoke() {
        repository.refresh()
    }
}