package com.example.newsportal.app.topnews

import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.example.newsportal.domain.model.Article
import com.example.newsportal.domain.usecases.GetNewsUseCase
import com.example.newsportal.utils.ResultWrapper
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsViewModel (
    private val getNewsUseCase: GetNewsUseCase
): ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _data = MutableLiveData<List<Article>>()
    val data: LiveData<List<Article>> = _data

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        viewModelScope.launch {

            getNewsUseCase().collect { handleResult(it) }
        }
    }

    private fun handleResult(result: ResultWrapper<List<Article>>) {
        when (result) {
            is ResultWrapper.Success -> {
                _data.value = result.data!!
                setLoading(false)
            }
            is ResultWrapper.Error -> {
                _error.value = result.msg
                setLoading(false)
            }
            ResultWrapper.Loading -> setLoading(true)
        }
    }

    private fun setLoading(value: Boolean) {
        _isLoading.value = value
    }

}