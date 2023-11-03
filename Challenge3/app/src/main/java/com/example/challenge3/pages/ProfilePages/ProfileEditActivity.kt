package com.example.challenge3.pages.ProfilePages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.challenge3.databinding.ActivityProfileEditBinding
import com.example.challenge3.models.User
import com.example.challenge3.models.interfaces.DialogReauthenticateListener
import com.example.challenge3.pages.Dialogs.DialogLoading
import com.example.challenge3.pages.Dialogs.DialogReauthenticate
import com.example.challenge3.util.ShowSnackbarCustom
import com.example.challenge3.util.preferences.PreferencesHelper
import com.example.challenge3.util.viewmodels.ProfileEditViewModel
import org.koin.android.ext.android.inject

class ProfileEditActivity : AppCompatActivity(),DialogReauthenticateListener {
    private lateinit var binding:ActivityProfileEditBinding
    private val viewModel:ProfileEditViewModel by inject()
    private lateinit var dialogLoading: DialogLoading

    private var user:User? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var loadingFragment = false
        binding=ActivityProfileEditBinding.inflate(layoutInflater)
        user = PreferencesHelper.getUser()

        dialogLoading= DialogLoading()

        binding.etUsernameProfileEditmode.setText(user?.username)
        binding.etTeleponProfileEditmode.setText(user?.telepon)

        binding.ibCancelEditProfile.setOnClickListener {
            finish()
        }

        viewModel.isUpdate.observe(this){
            when(it){
                3->{
                   ShowSnackbarCustom(message = null, title = "Failed to update",400,binding.root)
                    binding.ivConfirmEditprofile.visibility=View.VISIBLE
                }
                2->{
                    if (!loadingFragment){
                        loadingFragment=true
                        dialogLoading.isCancelable=false
                        dialogLoading.show(
                            this.supportFragmentManager,"dialogLoading"
                        )
                    }
                }
                1->{
                    val newUser = User(user!!.username,user!!.email,user!!.telepon)
                    newUser.username=binding.etUsernameProfileEditmode.text.toString()
                    newUser.telepon=binding.etTeleponProfileEditmode.text.toString()
                    PreferencesHelper.saveUser(newUser)
                    dialogLoading.removeDialog()
                    finish()
                }
            }
        }

        binding.ivConfirmEditprofile.setOnClickListener {

            if(!binding.etPasswordEditmode.text.toString().isNullOrEmpty())
            {
                ShowReauthenticateDialog()
            }

            else{
                //for update firestore only
                val newUser = User(user!!.username,user!!.email,user!!.telepon)
                Log.d("Firebase","${newUser.username}---${user!!.username}")
                newUser.username=binding.etUsernameProfileEditmode.text.toString()
                newUser.telepon=binding.etTeleponProfileEditmode.text.toString()
                Log.d("Firebase","${newUser.username}---${user!!.username}")
                viewModel.updateProfile(user!!,newUser)
            }
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
        newUser.username=binding.etUsernameProfileEditmode.text.toString()
        newUser.telepon=binding.etTeleponProfileEditmode.text.toString()

        if(oldPassword!=binding.etPasswordEditmode.text.toString()&&!binding.etPasswordEditmode.text.toString().isNullOrEmpty()){
            Log.d("profile edit","Updating password")
            viewModel.updateAuthPassword(user!!,newUser,oldPassword,
                binding.etPasswordEditmode.text.toString())
        }

    }

    override fun onDataReceived(data: String) {
        if(data!=""){
            EditAuth(data)
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.isUpdate.observe(this){
                Log.d("onPause",it.toString())
        }
    }
}