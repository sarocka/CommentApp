package com.example.catapp.repositories

import com.example.catapp.api.RetrofitInstance
import com.example.catapp.model.Comment
import retrofit2.Response

class CommentRepository {


    suspend fun getComments(): Response<List<Comment>> {
        return RetrofitInstance.api.getComments()
    }

    suspend fun deleteComment(position: Int): Response<Void> {
        return RetrofitInstance.api.deleteComment(position)
    }

    suspend fun editComment(comment: Comment, id: Int): Response<Comment> {
        return RetrofitInstance.api.editComment(comment, id)
    }

}