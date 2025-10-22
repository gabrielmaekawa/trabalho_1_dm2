package com.example.myapplication.network

import com.example.myapplication.model.Item
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    // Change the return type here
    @GET("api/dados")
    suspend fun getData(): Response<List<Item>>
}