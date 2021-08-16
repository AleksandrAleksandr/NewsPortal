package com.example.newsportal.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsportal.data.local.model.ArticleLocal

@Dao
interface NewsDao {

    @Query("SELECT * from articlelocal")
    fun findAll(): LiveData<List<ArticleLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(news: List<ArticleLocal>)
}