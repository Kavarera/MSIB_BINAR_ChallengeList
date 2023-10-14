package com.example.challenge3.viewmodels

import android.app.Application
import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challenge3.models.EnumListFragment


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
            EnumListFragment.RIWAYAT-> _currentFragment.value=EnumListFragment.RIWAYAT
            EnumListFragment.PROFILE -> _currentFragment.value = EnumListFragment.PROFILE
            else -> throw IllegalArgumentException("Invalid fragmentId: $fragmentId")
        }
    }
}