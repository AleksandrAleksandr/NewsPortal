package com.example.newsportal.data.remote.model

import com.example.newsportal.data.remote.model.ArticleNetwork

data class NewsResponce(val status: String?, val totalResults: Int?, val articles: List<ArticleNetwork>?)
