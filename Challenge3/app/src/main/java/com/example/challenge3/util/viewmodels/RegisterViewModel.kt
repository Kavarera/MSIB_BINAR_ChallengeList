package com.example.challenge3.util.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel(application:Application): ViewModel() {
    private val _username = MutableLiveData<String>()
    val username:LiveData<String> get()=_username

    private val _password = MutableLiveData<String>()
    val password:LiveData<String> get()=_password
    private val _email = MutableLiveData<String>()
    val email:LiveData<String> get()=_email

    private val _telepon = MutableLiveData<String>()
    val telepon:LiveData<String> get()=_telepon
}