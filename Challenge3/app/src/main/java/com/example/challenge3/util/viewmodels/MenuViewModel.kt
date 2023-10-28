package com.example.challenge3.util.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.challenge3.repository.MenuRepository
import com.example.challenge3.util.Resource
import com.example.challenge3.util.networking.ApiRetrofit.ApiService
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class MenuViewModel(private val apiService: ApiService):ViewModel() {


    fun fetchAllFoods(category: String? = null) = liveData(Dispatchers.IO){
        try {
            if(category!=null){
                emit(Resource.success(data = apiService.getFoodsWithCategory(category)))
            }
            else{
                emit(Resource.success(data=apiService.getAllFoods()))
            }
        }catch (e:Exception){
            emit(
                Resource.error(data=apiService.getAllFoods(),
                    message = e.message?:"Error when getting all foods"
                ))
        }
    }

    fun fetchCategories() = liveData(Dispatchers.IO){
        try {
            emit(Resource.success(data = apiService.getCategory2()))
        }catch (e:Exception){
            emit(Resource.error(data=null,
                message=e.message ?:"Error when getting all categories"))
        }
    }
}