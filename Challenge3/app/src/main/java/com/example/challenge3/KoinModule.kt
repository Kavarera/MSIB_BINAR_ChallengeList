package com.example.challenge3

import android.app.Application
import com.example.challenge3.repository.MenuRepository
import com.example.challenge3.util.networking.ApiRetrofit.ApiClient
import com.example.challenge3.util.viewmodels.MenuViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinModule {

    val Application.dataModule get()= module {

        single { ApiClient.instance }

        factory { MenuRepository(get(),get()) }
    }

    val Application.uiModule get()= module {
        viewModel { MenuViewModel(get()) }

    }

}