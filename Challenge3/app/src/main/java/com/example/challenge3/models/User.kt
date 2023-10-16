package com.example.challenge3.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize


@Parcelize
data class User(
    var id:Int=0,
    var username:String,
    var password:String,
    var email:String,
    var telepon:String
):Parcelable{
    fun toJson():String{
        return Gson().toJson(this)
    }
    companion object{
        fun fromJson(json:String):User{
            return Gson().fromJson(json,User::class.java)
        }
    }
}
