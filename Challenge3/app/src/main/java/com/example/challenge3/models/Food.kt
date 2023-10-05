package com.example.challenge3.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class Food(
    var id:Int,
    var Name:String,
    var description:String,
    var imageId:Int,
    var Price:String,
    var location:String,
    var urlLocation:String
) : Parcelable
