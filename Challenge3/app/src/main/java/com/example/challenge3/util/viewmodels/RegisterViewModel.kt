package com.example.challenge3.util.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge3.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class RegisterViewModel(): ViewModel() {
    private val _username = MutableLiveData<String>()
    val username:LiveData<String> get()=_username

    private val _password = MutableLiveData<String>()
    val password:LiveData<String> get()=_password
    private val _email = MutableLiveData<String>()
    val email:LiveData<String> get()=_email

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
            Log.d("Firebase",e.message.toString())
        }
    }

    fun resetRegisterStatus(){
        _isRegister.postValue(false)
    }

}