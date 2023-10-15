package com.example.challenge3.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challenge3.database.repository.UserRepository
import com.example.challenge3.models.User

class LoginViewModel(application: Application): ViewModel() {
    val repository:UserRepository = UserRepository(application)
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

    fun getUser(username:String,password:String): User?{
        return repository.getUser(username,password)
    }
}