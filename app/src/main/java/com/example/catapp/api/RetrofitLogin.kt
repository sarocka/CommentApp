package com.example.catapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitLogin {

    private val retrofit by lazy {
        Retrofit.Builder().baseUrl("https://reqres.in")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    val api by lazy {
        retrofit.create(LoginEndpoint::class.java)
    }
}