package com.example.catapp.repositories

import com.example.catapp.api.LoginRequest
import com.example.catapp.api.LoginResponse
import com.example.catapp.api.RetrofitLogin
import retrofit2.Response

class LoginRepository {

    suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse> {
        return RetrofitLogin.api.loginUser(loginRequest)
    }
}