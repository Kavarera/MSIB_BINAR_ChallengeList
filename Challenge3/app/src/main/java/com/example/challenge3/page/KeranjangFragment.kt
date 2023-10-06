package com.example.challenge3.page

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge3.R
import com.example.challenge3.ViewModelFactory
import com.example.challenge3.adapters.KeranjangRecyclerViewAdapter
import com.example.challenge3.databinding.FragmentKeranjangBinding
import com.example.challenge3.models.FoodKeranjang
import com.example.challenge3.viewmodels.FoodViewModel

class KeranjangFragment : Fragment() {

    private lateinit var foodViewModel: FoodViewModel
    private lateinit var listPesanan:LiveData<List<FoodKeranjang>>
    private lateinit var binding :FragmentKeranjangBinding
    private lateinit var adapter: KeranjangRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Viewmodel
        val viewModelFactory = ViewModelFactory(requireActivity().application)
        foodViewModel = ViewModelProvider(requireActivity(),viewModelFactory).get(FoodViewModel::class.java)
        listPesanan=foodViewModel.getAllFoods()
        Log.d("keranjang","list of pesanan ${listPesanan.value}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        //binding
        binding=FragmentKeranjangBinding.inflate(inflater,container,false)
        val recyclerView = binding.rvKeranjang
        listPesanan = foodViewModel.getAllFoods()
        listPesanan.observe(viewLifecycleOwner){item->
            var totalPrice:Int = 0
            item.forEach {
                var t =0
                t = it.quantity * it.foodPrice
                totalPrice +=t
            }
            binding.tvTotalHargaPesanan.text="Rp. ${totalPrice.toString()}"
            adapter = KeranjangRecyclerViewAdapter(item,foodViewModel)
            recyclerView.adapter=adapter
            recyclerView.layoutManager=LinearLayoutManager(requireContext())
        }

        binding.btnPesan.setOnClickListener {
            findNavController().navigate(R.id.action_keranjangFragment_to_konfirmasiPesananFragment)
        }


        // Inflate the layout for this fragment
        return binding.root
    }

}