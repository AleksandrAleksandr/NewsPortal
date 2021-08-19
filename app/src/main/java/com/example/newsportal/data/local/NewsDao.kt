package com.example.newsportal.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsportal.data.local.model.ArticleLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query("SELECT * from articlelocal")
    fun getAll(): Flow<List<ArticleLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(news: List<ArticleLocal>)
}