package com.example.challenge3.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Food(
    var Name:String,
    var description:String,
    val imageId:Int,
    val Price:String,
    val location:String,
    val urlLocation:String
) : Parcelable
