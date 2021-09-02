package com.example.newsportal.data.local

import androidx.room.*
import com.example.newsportal.data.local.model.ArticleBookmark
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBookmark(bookmark: ArticleBookmark)

    @Delete
    fun deleteBookmark(bookmark: ArticleBookmark)

    @Query("SELECT * from bookmarks")
    fun getBookmarks(): Flow<List<ArticleBookmark>>
}