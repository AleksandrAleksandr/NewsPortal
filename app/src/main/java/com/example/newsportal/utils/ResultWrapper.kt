package com.example.newsportal.utils

sealed class ResultWrapper<out T>{

    data class Success<T>(val data: T): ResultWrapper<T>()
    data class Error(val msg: String): ResultWrapper<Nothing>()
    object Loading: ResultWrapper<Nothing>()

    inline fun <R> map(fn: (T) -> (R)): ResultWrapper<R> =
        when(this){
            is Success -> Success(fn(data))
            is Error -> this
            Loading -> Loading
        }

    inline fun ifSuccess(fn: (T) -> Unit) {
        if (this is Success) fn(data)
    }

    inline fun ifError(fn: () -> Unit) {
        if (this is Error) fn()
    }
}