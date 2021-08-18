package com.example.newsportal.app.topnews

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.example.newsportal.data.NewsRepository
import com.example.newsportal.domain.model.Article
import com.example.newsportal.domain.usecases.GetNewsUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsViewModel (
    private val getNewsUseCase: GetNewsUseCase
): ViewModel() {

    val data = MutableLiveData<List<Article>>()

    init {
        viewModelScope.launch {

            val news = getNewsUseCase.invoke()
            news.collect { data.value = it }
        }
    }

}