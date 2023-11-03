package com.example.challenge3.repository

import com.example.challenge3.util.networking.ApiRetrofit.ApiService
import com.example.challenge3.util.networking.Request.OrderRequest
import com.example.challenge3.util.networking.Response.OrderResponse

class KeranjangPesananRepository(private val apiService: ApiService) {

    fun postOrder(orderRequest:OrderRequest):OrderResponse{
        val response = apiService.postOrder(orderRequest).execute()
        return response.body() as OrderResponse
    }

}