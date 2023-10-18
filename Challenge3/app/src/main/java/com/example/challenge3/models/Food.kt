package com.example.challenge3.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Food(
    @SerializedName("harga_format") val hargaString: String,
    val nama: String,
    val harga: Int,
    @SerializedName("image_url")val imageUrl: String,
    val detail: String,
    @SerializedName("alamat_resto")val alamatResto: String
) : Parcelable
//@Parcelize
//data class Food(
//    var id:Int,
//    var Name:String,
//    var description:String,
//    var imageId:Int,
//    var Price:String,
//    var location:String,
//    var urlLocation:String
//) : Parcelable
