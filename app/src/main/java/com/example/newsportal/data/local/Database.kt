package com.example.newsportal.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsportal.data.local.model.ArticleLocal

@Database(entities = [ArticleLocal::class], version = 1)
abstract class Database : RoomDatabase(){
    abstract val newsDao: NewsDao
}