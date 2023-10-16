package com.example.challenge3.util.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challenge3.database.repository.FoodKeranjangRepository
import com.example.challenge3.models.enumclass.EnumMetodePembayaran
import com.example.challenge3.models.enumclass.EnumMetodePengiriman
import com.example.challenge3.models.FoodKeranjang

class KonfirmasiPesananViewModel(application:Application):ViewModel() {
    private val _pengiriman = MutableLiveData<EnumMetodePengiriman>()
    val pengiriman : LiveData<EnumMetodePengiriman> get() = _pengiriman
    private val _pembayaran = MutableLiveData<EnumMetodePembayaran>()
    val pembayaran : LiveData<EnumMetodePembayaran> get() = _pembayaran

    init {
        _pengiriman.value= EnumMetodePengiriman.AMBIL_SENDIRI
        _pembayaran.value= EnumMetodePembayaran.TUNAI
    }
    private val repository:FoodKeranjangRepository= FoodKeranjangRepository(application)

    fun getAllFoods():LiveData<List<FoodKeranjang>>{
        return repository.allFoods()
    }
    fun tipePengiriman():LiveData<EnumMetodePengiriman>{
        return pengiriman
    }
    fun tipePembayaran():LiveData<EnumMetodePembayaran>{
        return pembayaran
    }
    fun switchMetodePengiriman(tujuan: EnumMetodePengiriman){
        if(tujuan!=pengiriman.value){
            _pengiriman.postValue(tujuan)
        }
    }
    fun switchMetodePembayaran(tujuan: EnumMetodePembayaran){
        if(tujuan!=pembayaran.value){
            _pembayaran.postValue(tujuan)
        }
    }

}