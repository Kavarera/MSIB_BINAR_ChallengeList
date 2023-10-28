package com.example.challenge3.util.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.challenge3.database.repository.FoodKeranjangRepository
import com.example.challenge3.models.FoodKeranjang
import com.example.challenge3.repository.MenuRepository

class KeranjangViewModel(private val repository: FoodKeranjangRepository):ViewModel() {
//    val repository:FoodKeranjangRepository = FoodKeranjangRepository(application)
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