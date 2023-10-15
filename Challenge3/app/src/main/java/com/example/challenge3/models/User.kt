package com.example.challenge3.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize


@Entity(tableName="user")
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = true) var id:Int=0,
    @ColumnInfo(name = "username") var username:String,
    @ColumnInfo(name = "password") var password:String,
    @ColumnInfo(name="email") var email:String,
    @ColumnInfo(name = "telepon") var telepon:String
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
