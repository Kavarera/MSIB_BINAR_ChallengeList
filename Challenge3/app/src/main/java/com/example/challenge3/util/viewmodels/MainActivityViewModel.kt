package com.example.challenge3.util.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge3.models.enumclass.EnumListFragment
import com.example.challenge3.util.networking.ApiRetrofit.ApiClient
import com.example.challenge3.util.networking.ApiRetrofit.ApiService
import com.example.challenge3.util.networking.Response.CategoryResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class MainActivityViewModel():ViewModel() {

    private val _currentFragment = MutableLiveData<EnumListFragment>()
    val currentFragment:LiveData<EnumListFragment> get() = _currentFragment
    private val _bottomNavStat = MutableLiveData<Boolean>()
    val bottomNavStat:LiveData<Boolean> get()= _bottomNavStat
    fun setVisibleBottomNav(stat:Boolean){
        _bottomNavStat.postValue(stat)
    }

    fun switchFragment(fragmentId: EnumListFragment){
        when(fragmentId){
            EnumListFragment.HOME-> _currentFragment.value = EnumListFragment.HOME
            EnumListFragment.KERANJANG -> _currentFragment.value = EnumListFragment.KERANJANG
            EnumListFragment.RIWAYAT-> _currentFragment.value= EnumListFragment.RIWAYAT
            EnumListFragment.PROFILE -> _currentFragment.value = EnumListFragment.PROFILE
            else -> throw IllegalArgumentException("Invalid fragmentId: $fragmentId")
        }
    }

    fun fetchDataCategories(){

        viewModelScope.launch {
            try{
                val categoryResponse:CategoryResponse=ApiClient.instance.getCategory2()
            }
            catch (e:Exception){
                Log.e("API",e.message.toString())
            }
        }
//  Tanpa menggunakan kotlin coroutines jika tidak salah

//        ApiClient.instance.getCategory()
//            .enqueue(object: Callback<CategoryResponse> {
//                override fun onResponse(
//                    call: Call<CategoryResponse>,
//                    response: Response<CategoryResponse>
//                ) {
//                    val body = response.body()
//                    Log.d("testAPI",body?.message.toString())
//                }
//
//                override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
//                    Log.d("testAPI",t.message.toString())
//                }
//
//            })
    }
}