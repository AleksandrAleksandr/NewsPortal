package com.example.newsportal.app.bookmarks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsportal.domain.model.Article
import com.example.newsportal.domain.usecases.DeleteBookmarkUseCase
import com.example.newsportal.domain.usecases.GetBookmarksUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BookmarksViewModel(
    private val getBookmarksUseCase: GetBookmarksUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase
): ViewModel() {

    private val _bookmarks = MutableLiveData<List<Article>>()
    val bookmarks: LiveData<List<Article>> = _bookmarks

    init {
        viewModelScope.launch {
            getBookmarksUseCase().collect { _bookmarks.value = it }
        }
    }

    fun onArticleSwiped(article: Article) {
        viewModelScope.launch {
            deleteBookmarkUseCase(article)
        }
    }
}