package com.example.catapp.api

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("token")
    val token: String,

    @SerializedName("error")
    val error: String,

    )
