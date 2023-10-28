package com.example.challenge3.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.challenge3.database.DatabaseApp
import com.example.challenge3.database.dao.IFoodKeranjangDao
import com.example.challenge3.models.FoodKeranjang
import com.example.challenge3.util.Resource
import com.example.challenge3.util.networking.ApiRetrofit.ApiService
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


//this repository inclde db and api service
class MenuRepository(application: Application,private val apiService:ApiService) {
    private val foodKeranjangDao:IFoodKeranjangDao
    private val executorService:ExecutorService=Executors.newSingleThreadExecutor()
    init{
        val db = DatabaseApp.getDatabase(application)
        foodKeranjangDao=db.keranjangPesananDao()
    }
    fun allFoods(): LiveData<List<FoodKeranjang>> {
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

    fun insert(food: FoodKeranjang){
        executorService.execute {
            foodKeranjangDao.insertPesanan(food)
        }
    }
    fun updateQuantity(quantity:Int, food: FoodKeranjang){
        executorService.execute {
            foodKeranjangDao
                .updateQuantityAndTotalPric(quantity,
                    food.harga*quantity,food.id)
        }
    }

    suspend fun fetchAllFood(category:String?=null) = apiService.getAllFoods()
//    liveData(Dispatchers.IO){
//        try {
//            if(category!=null){
//                emit(Resource.success(data = apiService.getFoodsWithCategory(category)))
//            }
//            else{
//                emit(Resource.success(data=apiService.getAllFoods()))
//            }
//        }catch (e:Exception){
//            emit(Resource.error(data=apiService.getAllFoods(), message = e.message?:"Error when getting all foods" ))
//        }
//    }

    suspend fun fetchCategories() = apiService.getCategory2()
}