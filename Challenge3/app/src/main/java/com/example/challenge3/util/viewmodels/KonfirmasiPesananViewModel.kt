package com.example.challenge3.util.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge3.database.repository.FoodKeranjangRepository
import com.example.challenge3.models.enumclass.EnumMetodePembayaran
import com.example.challenge3.models.enumclass.EnumMetodePengiriman
import com.example.challenge3.models.FoodKeranjang
import com.example.challenge3.models.User
import com.example.challenge3.util.networking.ApiRetrofit.ApiService
import com.example.challenge3.util.networking.Request.OrderRequest
import com.example.challenge3.util.networking.Request.OrdersItem
import com.example.challenge3.util.networking.Response.OrderResponse
import com.example.challenge3.util.preferences.PreferencesHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class KonfirmasiPesananViewModel(private val api:ApiService,application:Application):ViewModel() {
    private val _pengiriman = MutableLiveData<EnumMetodePengiriman>()
    val pengiriman : LiveData<EnumMetodePengiriman> get() = _pengiriman
    private val _pembayaran = MutableLiveData<EnumMetodePembayaran>()
    val pembayaran : LiveData<EnumMetodePembayaran> get() = _pembayaran

    private val _isSuccesfullySubmitOrder = MutableLiveData<Boolean>()
    val isSuccessfullySubmitOrder: LiveData<Boolean> get()=_isSuccesfullySubmitOrder

    init {
        _pengiriman.value= EnumMetodePengiriman.AMBIL_SENDIRI
        _pembayaran.value= EnumMetodePembayaran.TUNAI
    }
    private val repository:FoodKeranjangRepository= FoodKeranjangRepository(application)

    fun getAllFoods():LiveData<List<FoodKeranjang>>{
        return repository.allFoods()
    }

    fun sendOrder(context: Context, foods:List<OrdersItem>?, total:Int){
        lateinit var oResponse:OrderResponse
        viewModelScope.launch(Dispatchers.IO){
            try {
                val user = PreferencesHelper.getInstance(context)
                    .getUser(context)
                val orderRequest = OrderRequest(
                    total,foods,user?.username!!
                )
                val response = api.postOrder(orderRequest).execute()
                oResponse = response.body() as OrderResponse
                if(oResponse.code==201){
                    Log.d("OrderPost","Success Post Order")
                }
                _isSuccesfullySubmitOrder.postValue(oResponse.code==201)
            }catch (e:Exception){
                Log.e("OorderPost",e.message.toString())
            }
        }
    }
    fun resetSubmitOrder(){
        _isSuccesfullySubmitOrder.postValue(false)
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