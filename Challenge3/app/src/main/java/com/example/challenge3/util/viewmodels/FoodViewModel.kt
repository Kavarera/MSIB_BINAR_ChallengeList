package com.example.challenge3.util.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challenge3.database.DatabaseApp
import com.example.challenge3.database.dao.IFoodKeranjangDao
import com.example.challenge3.database.repository.FoodKeranjangRepository
import com.example.challenge3.models.Food
import com.example.challenge3.models.FoodKeranjang

class FoodViewModel(application: Application):ViewModel() {
    val repository:FoodKeranjangRepository = FoodKeranjangRepository(application)
    fun deleteAllFoods(){
        repository.deleteAllitem()
    }
    fun insertFood(food:FoodKeranjang){
        repository.insert(food)
    }
    fun getAllFoods():LiveData<List<FoodKeranjang>>{
        return repository.allFoods()
    }
    fun deleteById(id:Int){
        repository.deleteById(id)
    }
    fun updateQuantityandPrice(newValue:Int,food:FoodKeranjang){
        repository.updateQuantity(newValue,food)
    }
}