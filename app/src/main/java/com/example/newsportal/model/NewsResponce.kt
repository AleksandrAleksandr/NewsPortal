package com.example.newsportal.model

data class NewsResponce(val status: String, val totalResults: Int, val articles: List<Article>)
