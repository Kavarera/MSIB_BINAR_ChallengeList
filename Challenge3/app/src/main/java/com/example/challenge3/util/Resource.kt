package com.example.challenge3.util

import com.example.challenge3.models.enumclass.EnumStatus


data class Resource<out T>(val status:EnumStatus,val data:T?,val message: String?){
    companion object{
        fun<T> success(data:T): Resource<T> = Resource(status=EnumStatus.SUCCESS,data = data, message = null)
        fun<T> error(data:T,message: String): Resource<T> = Resource(status=EnumStatus.ERROR,data=data,message = message)
        fun <T> loading(data:T):Resource<T> = Resource(status=EnumStatus.LOADING,data=data,message = null)
    }
}

