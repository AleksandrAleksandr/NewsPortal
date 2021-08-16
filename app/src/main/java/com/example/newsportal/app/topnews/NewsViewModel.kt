package com.example.newsportal.app.topnews

import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.example.newsportal.data.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel (
    private val mRepository: NewsRepository
): ViewModel() {

    val data = mRepository.data

    init {
        viewModelScope.launch {
            mRepository.refresh()
        }
    }
}