package com.example.newsportal.data.remote

import com.example.newsportal.utils.ResultWrapper
import retrofit2.Response

abstract class BaseApiResponce {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ResultWrapper<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    //val articles = ((it as? NewsResponce)?.articles.orEmpty()) as R
                    return ResultWrapper.Success(body)
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): ResultWrapper<T> =
        ResultWrapper.Error("Api call failed $errorMessage")
}