package com.example.newsportal.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArticleLocal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String,
)