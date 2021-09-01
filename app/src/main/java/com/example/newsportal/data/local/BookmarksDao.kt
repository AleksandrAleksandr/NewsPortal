package com.example.newsportal.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsportal.data.local.model.ArticleBookmark

@Dao
interface BookmarksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBookmark(bookmark: ArticleBookmark)

    @Query("SELECT * from bookmarks")
    fun getBookmarks(): List<ArticleBookmark>
}