package com.example.newsportal.viewmodel

import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.example.newsportal.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel constructor(
    private val mRepository: NewsRepository
): ViewModel() {

    val data = mRepository.data

    init {
        viewModelScope.launch {
            mRepository.refresh()
        }
    }
}