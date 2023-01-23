package com.example.zooapp

import retrofit2.Response
import retrofit2.http.GET

interface AnimalsAPI {
    @GET("zoo_api.json")
    suspend fun getAnimals(): Response<List<Animal>>
}