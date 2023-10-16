package com.example.challenge3.util.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel(application: Application): ViewModel() {
    private val _username =MutableLiveData<String>()
    val username:LiveData<String> get()=_username
    private val _password = MutableLiveData<String>()
    val password:LiveData<String> get() = _password

    fun setPassword(newPassword:String){
        _password.postValue(newPassword)
    }

    fun setUsername(newUsername:String){
        _username.postValue(newUsername)
    }
}