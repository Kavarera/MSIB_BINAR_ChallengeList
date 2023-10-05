package com.example.challenge3

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.challenge3.viewmodels.FoodViewModel
import com.example.challenge3.viewmodels.MainActivityViewModel


@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val application: Application ) : ViewModelProvider.Factory {
    override fun<T:ViewModel> create(modelClass: Class<T>):T{
        if(modelClass.isAssignableFrom(FoodViewModel::class.java)){
            return FoodViewModel(application) as T
        }
        throw IllegalArgumentException("Unknow ViewModel Class")
    }
}