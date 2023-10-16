package com.example.challenge3.util.networking.ApiRetrofit

import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    //untuk mapping Endpoint API
    @GET("category-menu")
    fun getCategory()

    @GET("category-menu")
    suspend fun getCategory2()
    @POST("order")
    fun postOrder()

}