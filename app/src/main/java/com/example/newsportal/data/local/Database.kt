package com.example.newsportal.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsportal.data.local.model.ArticleBookmark
import com.example.newsportal.data.local.model.ArticleLocal

@Database(entities = [ArticleLocal::class, ArticleBookmark::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase(){
    abstract val newsDao: NewsDao

    abstract val bookmarksDao: BookmarksDao
}