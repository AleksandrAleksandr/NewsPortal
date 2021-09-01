package com.example.newsportal.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class ArticleBookmark (
    val category: String,
    val author: String,
    @PrimaryKey val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String,
)
