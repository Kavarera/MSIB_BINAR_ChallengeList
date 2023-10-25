package com.example.challenge3.util.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challenge3.models.User
import com.example.challenge3.util.preferences.PreferencesHelper

class ProfileVIewModel:ViewModel() {

    private val _User = MutableLiveData<User?>()
    val User:LiveData<User?> get()=_User

    fun getUserData(context: Context){
        _User.postValue(PreferencesHelper.getInstance(context)
            .getUser()
        )
    }
}