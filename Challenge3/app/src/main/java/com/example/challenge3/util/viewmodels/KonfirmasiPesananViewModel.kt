package com.example.challenge3.util.viewmodels


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge3.database.repository.FoodKeranjangRepository
import com.example.challenge3.models.enumclass.EnumMetodePembayaran
import com.example.challenge3.models.enumclass.EnumMetodePengiriman
import com.example.challenge3.repository.KeranjangPesananRepository
import com.example.challenge3.util.networking.Request.OrderRequest
import com.example.challenge3.util.networking.Request.OrdersItem
import com.example.challenge3.util.preferences.PreferencesHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class KonfirmasiPesananViewModel(private val kpRepository:KeranjangPesananRepository
):ViewModel() {
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

    fun sendOrder(foods:List<OrdersItem>?, total:Int){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val user = PreferencesHelper.getUser()
                val orderRequest = OrderRequest(
                    total,foods,user?.username!!
                )

                val oResponse=kpRepository.postOrder(orderRequest)
                if(oResponse.code==201){
                    Log.d("OrderPost","Success Post Order")
                }
                _isSuccesfullySubmitOrder.postValue(oResponse.code==201)
            }catch (e:Exception){
                Log.e("OrderPost",e.message.toString())
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