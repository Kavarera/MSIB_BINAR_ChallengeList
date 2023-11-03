package com.example.challenge3

import android.app.Application
import com.example.challenge3.database.repository.FoodKeranjangRepository
import com.example.challenge3.repository.KeranjangPesananRepository
import com.example.challenge3.repository.MenuRepository
import com.example.challenge3.util.networking.ApiRetrofit.ApiClient
import com.example.challenge3.util.viewmodels.FoodDetailViewModel
import com.example.challenge3.util.viewmodels.KeranjangViewModel
import com.example.challenge3.util.viewmodels.KonfirmasiPesananViewModel
import com.example.challenge3.util.viewmodels.LoginViewModel
import com.example.challenge3.util.viewmodels.MainActivityViewModel
import com.example.challenge3.util.viewmodels.MenuViewModel
import com.example.challenge3.util.viewmodels.ProfileEditViewModel
import com.example.challenge3.util.viewmodels.ProfileVIewModel
import com.example.challenge3.util.viewmodels.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinModule {

    val Application.dataModule get()= module {

        single { ApiClient.instance }

        //repo db
        factory { FoodKeranjangRepository(get()) }

        //--------- Repository
        factory { MenuRepository(get(),get()) }
        factory { KeranjangPesananRepository(get()) }
    }

    val Application.uiModule get()= module {
        viewModel { MenuViewModel(get()) }
        viewModel { KeranjangViewModel(get()) }
        viewModel { KonfirmasiPesananViewModel(get()) }
        viewModel{LoginViewModel()}
        viewModel{RegisterViewModel()}
        viewModel { ProfileEditViewModel() }
        viewModel{ FoodDetailViewModel() }
        single { MainActivityViewModel() }
        viewModel{ProfileVIewModel()}
    }

}