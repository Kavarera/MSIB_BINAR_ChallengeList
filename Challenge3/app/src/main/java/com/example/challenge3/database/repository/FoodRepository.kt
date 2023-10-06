package com.example.challenge3.database.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.challenge3.database.DatabaseApp
import com.example.challenge3.database.dao.IFoodKeranjangDao
import com.example.challenge3.models.Food
import com.example.challenge3.models.FoodKeranjang
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FoodKeranjangRepository(application: Application) {
    private val foodKeranjangDao:IFoodKeranjangDao
    private val executorService:ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = DatabaseApp.getDatabase(application)
        foodKeranjangDao=db.keranjangPesananDao()
    }
    fun allFoods():LiveData<List<FoodKeranjang>>{
        Log.d("room","Getting all pesanan")
        return foodKeranjangDao.getAllPesanan()
    }

    fun deleteAllitem(){
        executorService.execute {
            foodKeranjangDao.deleteAllItem()
        }
    }

    fun deleteById(id:Int){
        executorService.execute {
            foodKeranjangDao.deleteById(id)
        }
    }

    fun insert(food:FoodKeranjang){
        executorService.execute {
            foodKeranjangDao.insertPesanan(food)
        }
    }
    fun updateQuantity(quantity:Int, food:FoodKeranjang){
        executorService.execute {
            foodKeranjangDao
                .updateQuantityAndTotalPric(quantity,
                    food.foodPrice*quantity,food.id)
        }
    }
}