package com.example.challenge3.util.networking.ApiRetrofit

import android.util.Log
import com.example.challenge3.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

public object ApiClient {
    private const val BASE_URL = "https://192e8d21-8c33-437f-8011-83aab75d4ed0.mock.pstmn.io/"

    private val logging:HttpLoggingInterceptor
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger{
                override fun log(message: String) {
                    Log.d("httpLoggingInterceptor_BinarAPP",message)
                }
            })
            return httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level=HttpLoggingInterceptor.Level.BODY
            }
        }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        retrofit.create(ApiService::class.java)
    }
    var instanceTesting =  Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    var endPoint = instanceTesting.create(ApiService::class.java)

}