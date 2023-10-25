package com.example.challenge3.util.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import java.lang.Exception

class ProfileEditViewModel:ViewModel() {

    private val _isUpdate = MutableLiveData<Int>()
    val isUpdate:LiveData<Int> get()=_isUpdate
    /*
        1 = SUCCESS
        2 = WAITING / PROCESS
        3 = FAILED
    * */

    fun updateProfile(old:User,new:User){
        Log.d("Firebase","${new.username}---${old.username}")
        viewModelScope.launch(Dispatchers.IO) {
            updateStateStatus(2)
            Firebase.firestore.collection("data-msib-restaurant")
                .document(old.username)
                .delete()
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        Log.d("Firebase","Berhasil delete")
                    }
                    else{
                        Log.d("Firebase","Gagal delete")
                        updateStateStatus(3)
                    }
                }
            Firebase.firestore.collection("data-msib-restaurant")
                .document(new.username)
                .set(new)
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        Log.d("Firebase","Berhasil update")
                        updateStateStatus(1)
                    }
                    else{
                        Log.d("Firebase","Gagal update")
                        updateStateStatus(3)
                    }
                }

        }

    }
        fun updateAuthPassword(old: User,new:User,oldPassword:String,newPassword:String){
            viewModelScope.launch(Dispatchers.IO) {
                updateStateStatus(2)
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
                                        updateProfile(old=old,new=new)
                                    }
                                    else{
                                        Log.d("Firebase","Failed to update password")
                                        updateStateStatus(3)
                                    }
                                }
                        }
                        else{
                            Log.d("Firebase","Failed to reauthenticate")
                            updateStateStatus(3)
                        }
                    }



            }

        }
    fun updateStateStatus(status:Int){
        _isUpdate.postValue(status)
    }


}