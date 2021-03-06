package com.example.newsportal.app.topnews

import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.example.newsportal.domain.categories.Categories
import com.example.newsportal.domain.model.Article
import com.example.newsportal.domain.usecases.AddBookmarkUseCase
import com.example.newsportal.domain.usecases.GetNewsUseCase
import com.example.newsportal.domain.usecases.RefreshNewsUseCase
import com.example.newsportal.utils.ResultWrapper
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsViewModel (
    private val getNewsUseCase: GetNewsUseCase,
    private val addBookmarkUseCase: AddBookmarkUseCase,
    private val refreshNewsUseCase: RefreshNewsUseCase
): ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _newsSport = MutableLiveData<List<Article>>()
    private val _newsBusiness = MutableLiveData<List<Article>>()
    private val _newsGeneral = MutableLiveData<List<Article>>()
    private val _newsEntertainment = MutableLiveData<List<Article>>()
    private val _newsScience = MutableLiveData<List<Article>>()
    private val _newsTechnology = MutableLiveData<List<Article>>()
    private val _newsHealth = MutableLiveData<List<Article>>()

    init {
        viewModelScope.launch {

            getNewsUseCase().collect { handleResult(it) }
        }
    }

    fun newsByCategory(category: String): LiveData<List<Article>> =
        when (category) {
            Categories.health.toString() -> _newsHealth
            Categories.science.toString() -> _newsScience
            Categories.sports.toString() -> _newsSport
            Categories.entertainment.toString() -> _newsEntertainment
            Categories.general.toString() -> _newsGeneral
            Categories.technology.toString() -> _newsTechnology
            Categories.business.toString() -> _newsBusiness
            else -> MutableLiveData()
        }

    fun bookmarkSelected(article: Article) {
        viewModelScope.launch {
            addBookmarkUseCase(article)
        }
    }

    fun refresh() {
        viewModelScope.launch {
            refreshNewsUseCase()
            setLoading(false)
        }
    }

    private fun handleResult(result: ResultWrapper<List<Article>>) {
        when (result) {
            is ResultWrapper.Success -> {
                handleSuccess(result)
                setLoading(false)
            }
            is ResultWrapper.Error -> {
                _error.value = result.msg
                setLoading(false)
            }
            ResultWrapper.Loading -> setLoading(true)
        }
    }

    private fun handleSuccess(result: ResultWrapper.Success<List<Article>>) {
        with(result.data) {
            _newsSport.value = filter { it.category == Categories.sports.toString() }
            _newsScience.value = filter { it.category == Categories.science.toString() }
            _newsEntertainment.value = filter { it.category == Categories.entertainment.toString() }
            _newsGeneral.value = filter { it.category == Categories.general.toString() }
            _newsHealth.value = filter { it.category == Categories.health.toString() }
            _newsTechnology.value = filter { it.category == Categories.technology.toString() }
            _newsBusiness.value = filter { it.category == Categories.business.toString() }
        }
    }

    private fun setLoading(value: Boolean) {
        _isLoading.value = value
    }

}