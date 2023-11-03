package com.example.challenge3.repository

import android.app.Application
import com.example.challenge3.database.DatabaseApp
import com.example.challenge3.database.dao.IFoodKeranjangDao
import com.example.challenge3.util.networking.ApiRetrofit.ApiService


class MenuRepository(application: Application,private val apiService:ApiService) {
    private val foodKeranjangDao:IFoodKeranjangDao
    init{
        val db = DatabaseApp.getDatabase(application)
        foodKeranjangDao=db.keranjangPesananDao()
    }

    suspend fun fetchAllFood(category:String?=null)= if(category==null){
        apiService.getAllFoods()
    }else{
        apiService.getFoodsWithCategory(category)
    }

    suspend fun fetchCategories() = apiService.getCategory2()
}