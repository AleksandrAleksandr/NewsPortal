package com.example.newsportal.app.topnews

import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.example.newsportal.domain.model.Article
import com.example.newsportal.domain.usecases.GetNewsUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsViewModel (
    private val getNewsUseCase: GetNewsUseCase
): ViewModel() {

    private val _data = MutableLiveData<List<Article>>()
    val data: LiveData<List<Article>> = _data

    init {
        viewModelScope.launch {

            getNewsUseCase().collect { _data.value = it }
        }
    }

}