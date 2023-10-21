package com.example.challenge3.util.networking.ApiRetrofit

import com.example.challenge3.util.networking.Request.OrderRequest
import com.example.challenge3.util.networking.Response.CategoryResponse
import com.example.challenge3.util.networking.Response.FoodResponse
import com.example.challenge3.util.networking.Response.OrderResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    //untuk mapping Endpoint API
    @GET("category")
    fun getCategory():Call<CategoryResponse>

    @GET("category")
    suspend fun getCategory2():CategoryResponse

    @GET("listmenu")
    suspend fun getAllFoods():FoodResponse

    @GET("listmenu")
    suspend fun getFoodsWithCategory(@Query("c")category:String):FoodResponse

    @POST("order")
    fun postOrder(@Body orderRequest: OrderRequest):Call<OrderResponse>

}