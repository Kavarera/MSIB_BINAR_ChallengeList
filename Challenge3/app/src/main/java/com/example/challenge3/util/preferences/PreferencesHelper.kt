package com.example.challenge3.util.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.challenge3.models.enumclass.EnumRecyclerViewOption
import com.example.challenge3.models.User
import com.google.gson.Gson




object PreferencesHelper {
    private val PREF_NAME = "CHALLENGE3"
    private val KEY_APP_LAYOUT_SETTING = "MENULAYOUT"
    private val KEY_APP_USER = "USER_PREFERENCES"
    private lateinit var prefs:SharedPreferences

    fun init(context: Context){
        prefs=context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
    }

    var layoutOption:Int
        get() = prefs.getInt(KEY_APP_LAYOUT_SETTING, EnumRecyclerViewOption.LINEAR_LAYOUT.value)
        set(value) {
            prefs.edit().putInt(KEY_APP_LAYOUT_SETTING,value).apply()
        }


    fun saveUser(user: User){
        prefs.edit()
            .putString(KEY_APP_USER,Gson().toJson(user)).apply()
    }
    fun getUser():User?{
        val jsonString= prefs.getString(KEY_APP_USER,null)
        return jsonString?.let {
            User.fromJson(it)
        }
    }
    fun clearUser() {
        prefs.edit()
            .remove(KEY_APP_USER).apply()
    }
}