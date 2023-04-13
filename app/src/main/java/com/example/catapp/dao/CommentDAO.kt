package com.example.catapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.catapp.model.Comment

@Dao
interface CommentDAO {

    @Query("SELECT * FROM comment WHERE body LIKE '%' || :searchQuery || '%'")
    fun searchDatabase(searchQuery: String): LiveData<List<Comment>>

    @Query("SELECT * FROM comment ORDER BY id ASC")
    fun getComments(): LiveData<List<Comment>>

    @Query("SELECT COUNT(id) FROM comment")
    suspend fun getDataCount(): Int

    @Update
    suspend fun updateComment(comment: Comment)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addComment(newComment: Comment)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(comments: List<Comment>)

    @Delete
    suspend fun deleteComment(comment: Comment)

    @Query("DELETE FROM comment")
    suspend fun deleteAll()

}