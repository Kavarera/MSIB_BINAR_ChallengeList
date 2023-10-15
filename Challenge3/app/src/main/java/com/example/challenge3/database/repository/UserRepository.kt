package com.example.challenge3.database.repository

import android.app.Application
import com.example.challenge3.database.DatabaseApp
import com.example.challenge3.database.dao.IUserDao
import com.example.challenge3.models.User
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val userDao:IUserDao
    private val executorService:ExecutorService=Executors
        .newSingleThreadExecutor()
    init {
        userDao = DatabaseApp
            .getDatabase(application)
            .userDao()
    }

    fun getUser(username:String,password:String):User?{
        return userDao.getUser(username,password)
    }

    fun addUser(user:User){
        executorService.execute {
            userDao.insertUser(user)
        }
    }
}