package com.example.challenge3.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.challenge3.models.FoodKeranjang

@Dao
interface IFoodKeranjangDao {
    @Insert fun insertPesanan(foodkeranjang:FoodKeranjang)

    @Query("SELECT * FROM food_keranjang") fun getAllPesanan():LiveData<List<FoodKeranjang>>

    @Query("DELETE FROM food_keranjang") fun deleteAllItem()

    @Query("DELETE FROM food_keranjang WHERE id = :itemId") fun deleteById(itemId:Int)

    @Query("UPDATE food_keranjang SET food_quantity = :newQuantity, total_price = :newPrice WHERE id = :foodId")
    fun updateQuantityAndTotalPric(newQuantity:Int,newPrice:Int,foodId:Int)
}