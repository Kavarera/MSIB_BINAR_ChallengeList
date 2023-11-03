package com.example.challenge3.pages.MainPages

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge3.R
import com.example.challenge3.util.adapter.KeranjangRecyclerViewAdapter
import com.example.challenge3.databinding.FragmentKonfirmasiPesananBinding
import com.example.challenge3.pages.Dialogs.DialogPesananBerhasil
import com.example.challenge3.models.enumclass.EnumMetodePembayaran
import com.example.challenge3.models.enumclass.EnumMetodePengiriman
import com.example.challenge3.models.FoodKeranjang
import com.example.challenge3.util.networking.Request.OrdersItem
import com.example.challenge3.util.viewmodels.KeranjangViewModel
import com.example.challenge3.util.viewmodels.KonfirmasiPesananViewModel
import com.example.challenge3.util.viewmodels.MainActivityViewModel
import org.koin.android.ext.android.inject

class KonfirmasiPesananFragment : Fragment() {

    private val foodViewModel: KeranjangViewModel by inject()
    private val keranjangViewModel: KonfirmasiPesananViewModel by inject()
    private val mainViewModel: MainActivityViewModel by inject()
    private lateinit var binding:FragmentKonfirmasiPesananBinding
    private lateinit var listPesanan:LiveData<List<FoodKeranjang>>
    private lateinit var adapter: KeranjangRecyclerViewAdapter
    private lateinit var pembayaran: LiveData<EnumMetodePembayaran>
    private lateinit var pengiriman: LiveData<EnumMetodePengiriman>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.setVisibleBottomNav(false)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKonfirmasiPesananBinding.inflate(inflater,container,false)
        val recyclerView = binding.rvListKeranjang
        listPesanan = foodViewModel.getAllFoods()
        listPesanan.observe(viewLifecycleOwner){
            item->
            if(item.size==0){
                findNavController().popBackStack();
            }
            var t =0
            item.forEach {
                t = t+(it.quantity *it.harga)
            }
            binding.tvHargaTotalPesanan.text="Rp. $t"
            adapter= KeranjangRecyclerViewAdapter(requireContext(),item,foodViewModel)
            recyclerView.adapter=adapter
            recyclerView.layoutManager=LinearLayoutManager(requireContext())

            binding.btnCheckout.setOnClickListener {
                var total = 0

                val mappedListPesanan: LiveData<List<OrdersItem>> = Transformations.map(listPesanan) {
                    val mappedList = mutableListOf<OrdersItem>()

                    for (foodKeranjang in it) {
                        total = total+foodKeranjang.harga
                        val ordersItem = OrdersItem(
                            foodKeranjang.foodName,
                            foodKeranjang.harga,
                            foodKeranjang.quantity,
                            foodKeranjang.catatan
                        )
                        Log.d("pesanan","mapped name = ${foodKeranjang.foodName}")
                        mappedList.add(ordersItem)
                    }

                    return@map mappedList
                }

                Log.d("pesanan",mappedListPesanan.value.toString())

                val response = keranjangViewModel.sendOrder(
                    mappedListPesanan.value,total)


            }


        }
        pembayaran = keranjangViewModel.tipePembayaran()
        pengiriman = keranjangViewModel.tipePengiriman()

        keranjangViewModel.isSuccessfullySubmitOrder.observe(viewLifecycleOwner){
            if(it){
                DialogPesananBerhasil().show(requireActivity().supportFragmentManager,"dialogPesananBerhasil")
                foodViewModel.deleteAllFoods()
                keranjangViewModel.resetSubmitOrder()
            }
        }

        pembayaran.observe(viewLifecycleOwner){
            if(it== EnumMetodePembayaran.TUNAI){
                binding.btnTunai.backgroundTintList= ColorStateList
                    .valueOf(ContextCompat
                        .getColor(requireContext()
                            ,R.color.grey))
                binding.btnDompetDigital.backgroundTintList= ColorStateList
                    .valueOf(ContextCompat
                        .getColor(requireContext()
                            ,R.color.purple_200))
            }
            else{
                binding.btnTunai.backgroundTintList= ColorStateList
                    .valueOf(ContextCompat
                        .getColor(requireContext()
                            ,R.color.purple_200))
                binding.btnDompetDigital.backgroundTintList= ColorStateList
                    .valueOf(ContextCompat
                        .getColor(requireContext()
                            ,R.color.grey))
            }
        }
        pengiriman.observe(viewLifecycleOwner){
            if(it== EnumMetodePengiriman.DIKIRIM){
                binding.btnDikirim.backgroundTintList= ColorStateList
                    .valueOf(ContextCompat
                        .getColor(requireContext()
                            ,R.color.grey))
                binding.btnAmbilSendiri.backgroundTintList= ColorStateList
                    .valueOf(ContextCompat
                        .getColor(requireContext()
                            ,R.color.purple_200))
            }
            else{
                binding.btnDikirim.backgroundTintList= ColorStateList
                    .valueOf(ContextCompat
                        .getColor(requireContext()
                            ,R.color.purple_200))
                binding.btnAmbilSendiri.backgroundTintList= ColorStateList
                    .valueOf(ContextCompat
                        .getColor(requireContext()
                            ,R.color.grey))
            }
        }
        binding.btnTunai.setOnClickListener {
            keranjangViewModel.switchMetodePembayaran(EnumMetodePembayaran.TUNAI)
        }
        binding.btnDompetDigital.setOnClickListener {
            keranjangViewModel.switchMetodePembayaran(EnumMetodePembayaran.DOMPET_DIGITAL)
        }

        binding.btnDikirim.setOnClickListener {
            keranjangViewModel.switchMetodePengiriman(EnumMetodePengiriman.DIKIRIM)
        }
        binding.btnAmbilSendiri.setOnClickListener {
            keranjangViewModel.switchMetodePengiriman(EnumMetodePengiriman.AMBIL_SENDIRI)
        }




       return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mainViewModel.setVisibleBottomNav(true)
    }

}