package com.example.challenge3.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.challenge3.models.User

@Dao
interface IUserDao {

    @Insert fun insertUser(user: User)

    @Query("SELECT * FROM user WHERE username=:username AND password=:password")
    fun getUser(username:String,password:String):User?

}