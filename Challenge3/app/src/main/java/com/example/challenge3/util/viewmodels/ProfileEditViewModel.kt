package com.example.challenge3.util.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge3.models.User
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.EmailAuthProvider
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
        fun updateAuth(old: User,new:User,oldPassword:String,newPassword:String){
            viewModelScope.launch(Dispatchers.IO) {
                val credential = EmailAuthProvider.getCredential(
                    old.email,oldPassword
                )

                FirebaseAuth.getInstance().currentUser?.reauthenticate(credential)
                    ?.addOnCompleteListener {
                        if(it.isSuccessful){
                            FirebaseAuth.getInstance().currentUser
                                ?.updatePassword(newPassword)
                                ?.addOnCompleteListener {result->
                                    if(result.isSuccessful){
                                        Log.d("Firebase","Succesfully update password")
                                    }
                                    else{
                                        Log.d("Firebase","Failed to update password")

                                    }
                                }
                        }
                        else{
                            Log.d("Firebase","Failed to reauthenticate\n${old.email}--$newPassword\n${credential==null}")
                        }
                    }



            }

        }
}