package com.example.challenge3.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FoodDetailViewModel : ViewModel() {
    private val _totalItem = MutableLiveData<Int>()
    val totalItem : LiveData<Int> get() = _totalItem
    init {
        _totalItem.value=1
    }

    fun addMoreItem(){
        val curr = _totalItem.value ?: 1
        _totalItem.value = curr+1
    }
    fun subMoreItem(){
        val curr = _totalItem.value?:1
        if(curr>1){
            _totalItem.value = curr-1
        }
    }
}