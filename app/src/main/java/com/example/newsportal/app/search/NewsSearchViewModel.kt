package com.example.newsportal.app.search

import androidx.lifecycle.*
import com.example.newsportal.domain.model.Article
import com.example.newsportal.domain.usecases.GetNewsSearchUseCase
import com.example.newsportal.utils.ResultWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

private const val TIMEOUT = 500L

@ExperimentalCoroutinesApi
@FlowPreview
class NewsSearchViewModel(
    private val getNewsSearchUseCase: GetNewsSearchUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _data = MutableLiveData<List<Article>>()
    val data: LiveData<List<Article>> = _data

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _query = MutableLiveData<String>()
    private val _interval = MutableLiveData<Pair<String, String>>()

    init {
        viewModelScope.launch {
            combine(inputQueryFlow(), dateIntervalFlow()) { query, interval ->
                Pair(query, interval)
            }.flatMapLatest { queryParams ->
                getNewsSearchUseCase(queryParams.first, queryParams.second)
            }.collect {
                handleResult(it)
            }
        }
    }

    private fun inputQueryFlow(): Flow<String> {
        return _query.asFlow()
            .debounce(TIMEOUT)
            .filter { query ->
                if (query.isEmpty()) {
                    _data.postValue(emptyList())
                    return@filter false
                } else {
                    return@filter true
                }
            }
            .distinctUntilChanged()
    }

    private fun dateIntervalFlow(): Flow<Pair<String, String>> {
        return _interval.asFlow()
    }

    fun queryTextChanged(newText: String?) {
        _query.value = newText.orEmpty()
    }

    fun dateIntervalChanged(newInterval: Pair<String, String>) {
        _interval.value = newInterval
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