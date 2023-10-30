package com.example.challenge3.util.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge3.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class RegisterViewModel(application:Application): ViewModel() {
    private val _username = MutableLiveData<String>()
    val username:LiveData<String> get()=_username

    private val _password = MutableLiveData<String>()
    val password:LiveData<String> get()=_password
    private val _email = MutableLiveData<String>()
    val email:LiveData<String> get()=_email

    private val _telepon = MutableLiveData<String>()
    val telepon:LiveData<String> get()=_telepon

    private val _isRegister = MutableLiveData<Boolean>()
    val isRegister get() = _isRegister

    init {
        _isRegister.postValue(false)
    }

    fun saveUserData(user: User){


        viewModelScope.launch (Dispatchers.IO){
            Firebase.firestore.collection("data-msib-restaurant")
                .document(user.username)
                .set(user)
                .addOnSuccessListener {
                    _isRegister.postValue(true)
                }
        }
    }

    fun registerUser(user:User,email:String,password:String){
        try {
            viewModelScope.launch(Dispatchers.IO) {
                Firebase.auth.createUserWithEmailAndPassword(
                    email,password
                ).addOnCompleteListener {
                    if(it.isSuccessful){

                        saveUserData(user)
                        Log.d("Firebase","berhasil auth create")
                        Firebase.auth.currentUser?.sendEmailVerification()
                    }
                    else{
                        Log.d("Firebase","Gagal auth create")
                    }
                }
            }
        } catch (e:Exception){
            Firebase.crashlytics.recordException(e)
            Log.d("Firebase",e.message.toString())
        }
    }

    fun resetRegisterStatus(){
        _isRegister.postValue(false)
    }

}