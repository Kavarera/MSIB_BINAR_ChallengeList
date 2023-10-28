package com.example.challenge3

import android.app.Application
import com.example.challenge3.KoinModule.dataModule
import com.example.challenge3.KoinModule.uiModule
import com.example.challenge3.util.preferences.PreferencesHelper
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        PreferencesHelper.init(this)

        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    dataModule,
                    uiModule
                )
            )
        }
    }
}