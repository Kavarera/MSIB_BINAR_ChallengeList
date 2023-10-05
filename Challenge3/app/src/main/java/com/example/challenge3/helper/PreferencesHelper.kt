package com.example.challenge3.helper

import android.content.Context
import android.content.SharedPreferences
import com.example.challenge3.models.EnumListFragment
import com.example.challenge3.models.RecyclerViewOption

class PreferencesHelper private constructor(context: Context) {
    private val PREF_NAME = "CHALLENGE3"
    private val KEY_APP_LAYOUT_SETTING = "MENULAYOUT"

    private val sharedPreferences: SharedPreferences = context
        .getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)

    companion object {
        @Volatile
        private var instance: PreferencesHelper? = null

        fun getInstance(context: Context): PreferencesHelper {
            return instance ?: synchronized(this) {
                instance ?: PreferencesHelper(context).also { instance = it }
            }
        }
    }

    var layoutOption:Int
        get() = sharedPreferences.getInt(KEY_APP_LAYOUT_SETTING,RecyclerViewOption.LINEAR_LAYOUT.value)
        set(value) {
            sharedPreferences.edit().putInt(KEY_APP_LAYOUT_SETTING,value).apply()
        }

}