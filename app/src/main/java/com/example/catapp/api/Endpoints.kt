package com.example.catapp.api

import com.example.catapp.model.Comment
import retrofit2.Response
import retrofit2.http.*

interface Endpoints {


    @GET("comments")
    suspend fun getComments(): Response<List<Comment>>

    @DELETE("comments/{id}")
    suspend fun deleteComment(@Path("id") id: Int): Response<Void>

    @PUT("comments/{id}")
    suspend fun editComment(@Body comment: Comment, @Path("id") id: Int): Response<Comment>

}