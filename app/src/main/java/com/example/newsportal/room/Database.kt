package com.example.newsportal.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsportal.model.ArticleLocal

@Database(entities = [ArticleLocal::class], version = 1)
abstract class Database : RoomDatabase(){
    abstract val newsDao: NewsDao
}