package com.example.challenge3.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.challenge3.database.dao.IFoodKeranjangDao
import com.example.challenge3.models.FoodKeranjang


@Database(entities = [FoodKeranjang::class], version = 3)
abstract class DatabaseApp:RoomDatabase() {

    abstract fun keranjangPesananDao():IFoodKeranjangDao

    companion object{
        @Volatile
        private var INSTANCE:DatabaseApp? = null
        @JvmStatic
        fun getDatabase(context:Context):DatabaseApp{
            if(INSTANCE==null){
                synchronized(DatabaseApp::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    DatabaseApp::class.java,"DatabaseApp")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as DatabaseApp
        }



    }
}