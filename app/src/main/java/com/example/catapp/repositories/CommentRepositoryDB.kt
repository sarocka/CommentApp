package com.example.catapp.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.catapp.dao.CommentDAO
import com.example.catapp.model.Comment


class CommentRepositoryDB(private val commentDao: CommentDAO) {

    val comments: LiveData<List<Comment>> = commentDao.getComments()


    fun getCommentsDB(): LiveData<List<Comment>> {
        Log.d("CommentRepositoryDB", "Repository: ${commentDao.getComments().value}")
        return commentDao.getComments()
    }

    suspend fun addComment(newComment: Comment) {
        Log.d("CommentRepositoryDB", "Repository: add method")
        commentDao.addComment(newComment)
    }

    suspend fun getDataCount(): Int {
        return commentDao.getDataCount()
    }

    suspend fun updateComment(comment: Comment) {
        Log.d("CommentRepositoryDB", "This is edited comment: ${comment}")
        commentDao.updateComment(comment)
    }

    suspend fun deleteComment(comment: Comment) {
        Log.d("CommentRepositoryDB", "Comment for deletion: ${comment}")
        commentDao.deleteComment(comment)

    }

    fun searchDatabase(query: String): LiveData<List<Comment>> {
        return commentDao.searchDatabase(query)
    }

    suspend fun insertAll(comments: List<Comment>) {
        commentDao.insertAll(comments)
    }

    override fun toString(): String {
        return "CommentRepository DB"
    }

    suspend fun deleteAll() {
        commentDao.deleteAll()
    }
}