package com.example.challenge3


import com.example.challenge3.util.networking.ApiRetrofit.ApiClient
import org.junit.Test

import org.junit.Assert.*
class ApiClientTest {

    @Test
    fun checkUrlBase(){
        var test = ApiClient.instanceTesting
        assertEquals(BuildConfig.API_URL, test.baseUrl().toUrl().toString())
    }

}