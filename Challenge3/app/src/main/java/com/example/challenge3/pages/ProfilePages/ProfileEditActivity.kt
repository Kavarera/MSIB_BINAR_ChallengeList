package com.example.challenge3.pages.ProfilePages

import android.app.Activity
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import com.example.challenge3.R
import com.example.challenge3.databinding.ActivityProfileEditBinding
import com.example.challenge3.models.User
import com.example.challenge3.models.interfaces.DialogReauthenticateListener
import com.example.challenge3.pages.Dialogs.DialogReauthenticate
import com.example.challenge3.util.preferences.PreferencesHelper
import com.example.challenge3.util.viewmodels.ProfileEditViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileEditActivity : AppCompatActivity(),DialogReauthenticateListener {
    private lateinit var binding:ActivityProfileEditBinding
    private val viewModel by viewModels<ProfileEditViewModel>()

    private var user:User? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProfileEditBinding.inflate(layoutInflater)
        user = PreferencesHelper.getInstance(this)
            .getUser(this)

        binding.etUsernameProfileEditmode.setText(user?.username)
        binding.etTeleponProfileEditmode.setText(user?.telepon)
        //binding.etEmailEditmode.setText(user?.email)

        binding.ibCancelEditProfile.setOnClickListener {
            finish()
        }

        binding.ivConfirmEditprofile.setOnClickListener {

            if(!binding.etPasswordEditmode.text.toString().isNullOrEmpty())
            {
                ShowReauthenticateDialog()
            }

            //for update firestore
            val newUser = User(user!!.username,user!!.email,user!!.telepon)
            Log.d("Firebase","${newUser.username}---${user!!.username}")
            newUser.username=binding.etUsernameProfileEditmode.text.toString()
            newUser.telepon=binding.etTeleponProfileEditmode.text.toString()
            Log.d("Firebase","${newUser.username}---${user!!.username}")
            viewModel.updateProfile(user!!,newUser)
        }


        setContentView(binding.root)
    }

    fun ShowReauthenticateDialog(){
        val dialog = DialogReauthenticate()
        dialog.setListener(this)
        dialog.show(this.supportFragmentManager,"DialogReauthenticate")
    }

    fun EditAuth(oldPassword:String){
        val newUser = User(user!!.username,
            user!!.email,
            user!!.telepon)

        if(oldPassword!=binding.etPasswordEditmode.text.toString()&&!binding.etPasswordEditmode.text.toString().isNullOrEmpty()){
            Log.d("profile edit","Updating password")
            viewModel.updateAuthPassword(user!!,newUser,oldPassword,
                binding.etPasswordEditmode.text.toString())
        }
//        if(binding.etEmailEditmode.text.toString()!=user!!.email){
//            Log.d("profile edit","Updating email ${user!!.email} -----> ${newUser.email}")
//            viewModel.updateAuthEmail(user!!,newUser,oldPassword)
//        }

    }

    override fun onDataReceived(data: String) {
        if(data!=""){
            EditAuth(data)
        }
    }
}