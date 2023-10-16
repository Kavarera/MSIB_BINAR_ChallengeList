package com.example.challenge3.util.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.challenge3.models.enumclass.EnumRecyclerViewOption
import com.example.challenge3.models.User
import com.google.gson.Gson

class PreferencesHelper private constructor(context: Context) {
    private val PREF_NAME = "CHALLENGE3"
    private val KEY_APP_LAYOUT_SETTING = "MENULAYOUT"
    private val KEY_APP_USER = "USER_PREFERENCES"

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
        get() = sharedPreferences.getInt(KEY_APP_LAYOUT_SETTING, EnumRecyclerViewOption.LINEAR_LAYOUT.value)
        set(value) {
            sharedPreferences.edit().putInt(KEY_APP_LAYOUT_SETTING,value).apply()
        }

    fun saveUser(context: Context,user:User){
        sharedPreferences.edit()
            .putString(KEY_APP_USER,Gson().toJson(user)).apply()
    }
    fun getUser(context: Context):User?{
        val jsonString= sharedPreferences.getString(KEY_APP_USER,null)
        return jsonString?.let {
            User.fromJson(it)
        }
    }
    fun clearUser(context: Context){
        sharedPreferences.edit()
            .remove(KEY_APP_USER).apply()
    }
}