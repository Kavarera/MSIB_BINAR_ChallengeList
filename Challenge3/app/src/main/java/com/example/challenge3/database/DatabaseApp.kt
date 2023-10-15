package com.example.challenge3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.challenge3.database.dao.IFoodKeranjangDao
import com.example.challenge3.database.dao.IUserDao
import com.example.challenge3.models.FoodKeranjang
import com.example.challenge3.models.User


@Database(entities = [FoodKeranjang::class,User::class], version = 1)
abstract class DatabaseApp:RoomDatabase() {

    abstract fun keranjangPesananDao():IFoodKeranjangDao
    abstract fun userDao():IUserDao

    companion object{
        @Volatile
        private var INSTANCE:DatabaseApp? = null
        @JvmStatic
        fun getDatabase(context:Context):DatabaseApp{
            if(INSTANCE==null){
                synchronized(DatabaseApp::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    DatabaseApp::class.java,"DatabaseApp").build()
                }
            }
            return INSTANCE as DatabaseApp
        }

    }
}