package com.example.catapp.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginEndpoint {

    @POST("/api/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>
}