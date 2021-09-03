package com.example.newsportal.domain.model

import java.io.Serializable

data class Article(
    val category: String,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String,
    val source: String,
    var isBookmarked: Boolean = false
) : Serializable
