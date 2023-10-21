package com.example.challenge3.util.viewmodelsfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.challenge3.util.networking.ApiRetrofit.ApiService
import com.example.challenge3.util.viewmodels.MenuViewModel

@Suppress("UNCHECKED_CAST")
class ApiViewModelFactory(val apiService: ApiService):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MenuViewModel::class.java)){
            return MenuViewModel(apiService) as T
        }

        else{
            throw IllegalArgumentException("Unknown Viewmodel class name")
        }
    }
}