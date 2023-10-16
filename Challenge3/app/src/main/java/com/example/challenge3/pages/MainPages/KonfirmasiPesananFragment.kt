package com.example.challenge3.pages.MainPages

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge3.R
import com.example.challenge3.util.viewmodelsfactory.PageViewModelFactory
import com.example.challenge3.util.adapter.KeranjangRecyclerViewAdapter
import com.example.challenge3.databinding.FragmentKonfirmasiPesananBinding
import com.example.challenge3.pages.Dialogs.DialogPesananBerhasil
import com.example.challenge3.models.enumclass.EnumMetodePembayaran
import com.example.challenge3.models.enumclass.EnumMetodePengiriman
import com.example.challenge3.models.FoodKeranjang
import com.example.challenge3.util.viewmodels.FoodViewModel
import com.example.challenge3.util.viewmodels.KonfirmasiPesananViewModel
import com.example.challenge3.util.viewmodels.MainActivityViewModel

class KonfirmasiPesananFragment : Fragment() {

    private lateinit var foodViewModel: FoodViewModel
    private lateinit var keranjangViewModel: KonfirmasiPesananViewModel
    private lateinit var mainViewModel: MainActivityViewModel
    private lateinit var binding:FragmentKonfirmasiPesananBinding
    private lateinit var listPesanan:LiveData<List<FoodKeranjang>>
    private lateinit var adapter: KeranjangRecyclerViewAdapter
    private lateinit var pembayaran: LiveData<EnumMetodePembayaran>
    private lateinit var pengiriman: LiveData<EnumMetodePengiriman>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        foodViewModel = ViewModelProvider(requireActivity(),
            PageViewModelFactory(requireActivity().application)
        )
            .get(FoodViewModel::class.java)

        keranjangViewModel=ViewModelProvider(requireActivity(),
            PageViewModelFactory(requireActivity().application)
        )
            .get(KonfirmasiPesananViewModel::class.java)

        mainViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
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
                t = t+(it.quantity *it.foodPrice)
            }
            binding.tvHargaTotalPesanan.text="Rp. ${t.toString()}"
            adapter= KeranjangRecyclerViewAdapter(item,foodViewModel)
            recyclerView.adapter=adapter
            recyclerView.layoutManager=LinearLayoutManager(requireContext())
        }
        pembayaran = keranjangViewModel.tipePembayaran()
        pengiriman = keranjangViewModel.tipePengiriman()

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
        binding.btnCheckout.setOnClickListener {
            DialogPesananBerhasil().show(requireActivity().supportFragmentManager,"dialogPesananBerhasil")
            foodViewModel.deleteAllFoods()
        }




       return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mainViewModel.setVisibleBottomNav(true)
    }

}