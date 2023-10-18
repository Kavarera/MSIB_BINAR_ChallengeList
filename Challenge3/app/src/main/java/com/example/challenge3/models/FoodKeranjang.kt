package com.example.challenge3.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "food_keranjang")
@Parcelize
data class FoodKeranjang(
    @PrimaryKey(autoGenerate = true) var id:Int=0,
    @ColumnInfo(name="food_name") var foodName:String,
    @ColumnInfo(name="image_id") var imageUrl:String,
    @ColumnInfo(name="food_harga") var harga:Int,
    @ColumnInfo(name="food_harga_text") var hargaString:String,
    @ColumnInfo(name = "food_quantity") var quantity:Int,
    @ColumnInfo(name="total_price") var totalPrice:Int,
    @ColumnInfo(name = "food_catatan") var catatan:String = "No Note"
) : Parcelable
