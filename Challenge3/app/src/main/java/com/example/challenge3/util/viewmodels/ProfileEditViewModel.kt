package com.example.challenge3.util.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge3.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProfileEditViewModel:ViewModel() {


    fun updateProfile(old:User,new:User){
        Log.d("Firebase","${new.username}---${old.username}")
        viewModelScope.launch(Dispatchers.IO) {
            Firebase.firestore.collection("data-msib-restaurant")
                .document(new.username)
                .set(new)
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        Log.d("Firebase","Berhasil update")
                    }
                    else{
                        Log.d("Firebase","Gagal update")
                    }
                }

            Firebase.firestore.collection("data-msib-restaurant")
                .document(old.username)
                .delete()
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        Log.d("Firebase","Berhasil delete")
                    }
                    else{
                        Log.d("Firebase","Gagal delete")
                    }
                }
        }
    }
}