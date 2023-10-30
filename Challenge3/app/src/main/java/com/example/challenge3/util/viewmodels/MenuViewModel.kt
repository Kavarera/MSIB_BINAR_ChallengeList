package com.example.challenge3.util.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.challenge3.models.CategoryFoodData
import com.example.challenge3.util.Resource
import com.example.challenge3.util.networking.ApiRetrofit.ApiClient
import com.example.challenge3.util.networking.ApiRetrofit.ApiService
import com.example.challenge3.util.networking.Response.CategoryResponse
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
            Firebase.crashlytics.recordException(e)
            emit(Resource.error(data=apiService.getAllFoods(), message = e.message?:"Error when getting all foods" ))
        }
    }


    fun fetchCategories() = liveData(Dispatchers.IO){
        try {
            emit(Resource.success(data = apiService.getCategory2()))
        }catch (e:Exception){
            Firebase.crashlytics.recordException(e)
            emit(Resource.error(data=null, message=e.message ?:"Error when getting all categories"))
        }
    }



//    fun fetchCategories():LiveData<List<CategoryFoodData>?>{
//        val categoriesLiveData = MutableLiveData<List<CategoryFoodData>?>()
//        ApiClient.instance.getCategory()
//            .enqueue(object: Callback<CategoryResponse> {
//                override fun onResponse(
//                    call: Call<CategoryResponse>,
//                    response: Response<CategoryResponse>
//                ) {
//                    val body = response.body()
//                    Log.d("testAPI",body?.message.toString())
//                }
//
//                override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
//                    Log.d("testAPI",t.message.toString())
//                }
//
//            })
//        return categoriesLiveData
//
//        val categoriesLiveData = MutableLiveData<List<CategoryFoodData>?>()
//        viewModelScope.launch {
//            try{
//                val categories= withContext(Dispatchers.IO){
//                    ApiClient.instance.getCategory2().data
//                }
//                categoriesLiveData.value=categories
//            }catch (e:Exception){
//                Log.d("MenuViewModel","Error while fetching categories data ${e.message.toString()}")
//                categoriesLiveData.postValue(null)
//            }
//        }
//        Log.d("testAPI","from MenuViewModel ${categoriesLiveData.value.toString()}")
//        return categoriesLiveData
//    }
}