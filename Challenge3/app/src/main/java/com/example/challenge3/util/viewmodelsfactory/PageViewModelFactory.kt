package com.example.challenge3.util.viewmodelsfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.challenge3.util.networking.ApiRetrofit.ApiService
import com.example.challenge3.util.viewmodels.KeranjangViewModel
import com.example.challenge3.util.viewmodels.KonfirmasiPesananViewModel
import com.example.challenge3.util.viewmodels.LoginViewModel
import com.example.challenge3.util.viewmodels.RegisterViewModel


@Suppress("UNCHECKED_CAST")
class PageViewModelFactory(private val application: Application,val apiService: ApiService) : ViewModelProvider.Factory {
    override fun<T:ViewModel> create(modelClass: Class<T>):T{
//        if(modelClass.isAssignableFrom(KeranjangViewModel::class.java)){
//            return KeranjangViewModel(application) as T
//        }
//        if(modelClass.isAssignableFrom(KonfirmasiPesananViewModel::class.java)){
//            return KonfirmasiPesananViewModel(apiService,application) as T
//        }
//        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
//            return LoginViewModel(application) as T
//        }
//        if(modelClass.isAssignableFrom(RegisterViewModel::class.java)){
//            return RegisterViewModel(application) as T
//        }
        throw IllegalArgumentException("Unknow ViewModel Class")
    }
}