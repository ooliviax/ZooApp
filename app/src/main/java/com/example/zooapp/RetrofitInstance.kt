package com.example.zooapp

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import kotlin.coroutines.coroutineContext

object RetrofitInstance {
    val api: AnimalsAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://ooliviax.github.io/jsonapi/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AnimalsAPI::class.java)
    }
}