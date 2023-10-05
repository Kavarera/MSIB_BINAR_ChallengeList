package com.example.challenge3.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.challenge3.database.DatabaseApp
import com.example.challenge3.database.dao.IFoodKeranjangDao
import com.example.challenge3.models.Food
import com.example.challenge3.models.FoodKeranjang

class FoodViewModel(private val application: Application):ViewModel() {
    private val foodKeranjangDao:IFoodKeranjangDao
    init {
        val db = DatabaseApp.getDatabase(application)
        foodKeranjangDao=db.foo
    }
    val allFoods:LiveData<List<FoodKeranjang>> = foodKeranjangDao.getAllPesanan()

    fun deleteAllitem(){
        foodKeranjangDao.deleteAllItem()
    }
}