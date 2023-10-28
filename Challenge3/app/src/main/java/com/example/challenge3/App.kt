package com.example.challenge3

import android.app.Application
import com.example.challenge3.util.preferences.PreferencesHelper

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        PreferencesHelper.init(this)
    }
}