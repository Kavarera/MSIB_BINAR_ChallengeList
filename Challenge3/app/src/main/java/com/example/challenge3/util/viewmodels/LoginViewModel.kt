package com.example.challenge3.util.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge3.models.User
import com.example.challenge3.util.preferences.PreferencesHelper
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class LoginViewModel(): ViewModel() {
    private val _username =MutableLiveData<String>()
    val username:LiveData<String> get()=_username
    private val _password = MutableLiveData<String>()
    val password:LiveData<String> get() = _password

    private val _IsLogin = MutableLiveData<Boolean>()
    val IsLogin:LiveData<Boolean> get()=_IsLogin

    init {
        _IsLogin.postValue(false)
    }

    fun setPassword(newPassword:String){
        _password.postValue(newPassword)
    }

    fun setUsername(newUsername:String){
        _username.postValue(newUsername)
    }

    suspend fun getUserData(username: String):Deferred<User?>{
        return viewModelScope.async(Dispatchers.IO) {
            var user: User? = null
            val doc = Firebase.firestore.collection("data-msib-restaurant")
                .document(username)
                .get()
                .await()
            if(doc!=null){
                Log.d("Login","Get email = ${user?.email}")
                user = User(
                    doc.getString("username")!!,doc.getString("email")!!,doc.getString("telepon")!!
                )
            }
            user
        }
    }

    fun Login(username:String, password:String,context:Context){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                var user = getUserData(username).await()
                Log.d("Login","Email received on Login Function ${user?.email}")
                if(user!=null){
                    Firebase.auth.signInWithEmailAndPassword(user.email,password)
                        .addOnCompleteListener {
                            if(it.isSuccessful){
//                                PreferencesHelper.getInstance(context)
//                                    .saveUser(user)
                                PreferencesHelper.saveUser(user)
                                Log.d("Login","Login Success on vm also success saving sharedpreferences")
                                
                                _IsLogin.postValue(true)
                            }
                            else{
                                Log.d("Login","Login Fail on vm")
                                _IsLogin.postValue(false)
                            }
                        }.await()
                }
            }
            catch (e:Exception){
                _IsLogin.postValue(false)
                Log.d("Login",e.message.toString())
            }
        }
    }


}