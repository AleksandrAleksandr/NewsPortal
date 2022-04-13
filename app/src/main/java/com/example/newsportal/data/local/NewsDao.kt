package com.example.newsportal.data.local

import androidx.room.*
import com.example.newsportal.data.local.model.ArticleLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query("SELECT * from articlelocal")
    fun getAll(): Flow<List<ArticleLocal>>

    @Query("SELECT * from articlelocal")
    fun getAllOneTime(): List<ArticleLocal>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(news: List<ArticleLocal>)

    @Update
    fun updateArticle(articleLocal: ArticleLocal)
}