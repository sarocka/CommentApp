package com.example.catapp.api
import android.util.Log
import com.example.catapp.LogInActivity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {

    val interceptor = HttpLoggingInterceptor();
    val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build();

    private val retrofit by lazy {

        val urlInput = LogInActivity.globalVar
        Log.d("RetrofitInstance", "Url is: ${urlInput}")
        // var url = "https://jsonplaceholder.typicode.com/"

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        Retrofit.Builder().baseUrl(urlInput).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    val api by lazy {
        retrofit.create(Endpoints::class.java) // creates implementation of the api endpoint defined in the interface
    }


}


