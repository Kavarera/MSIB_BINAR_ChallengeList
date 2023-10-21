package com.example.challenge3.pages.MainPages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge3.R
import com.example.challenge3.util.viewmodelsfactory.PageViewModelFactory
import com.example.challenge3.util.adapter.KeranjangRecyclerViewAdapter
import com.example.challenge3.databinding.FragmentKeranjangBinding
import com.example.challenge3.models.FoodKeranjang
import com.example.challenge3.util.networking.ApiRetrofit.ApiClient
import com.example.challenge3.util.viewmodels.FoodViewModel

class KeranjangFragment : Fragment() {

    private lateinit var foodViewModel: FoodViewModel
    private lateinit var listPesanan:LiveData<List<FoodKeranjang>>
    private lateinit var binding :FragmentKeranjangBinding
    private lateinit var adapter: KeranjangRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Viewmodel
        val viewModelFactory = PageViewModelFactory(requireActivity().application,
            ApiClient.instance)
        foodViewModel = ViewModelProvider(requireActivity(),viewModelFactory).get(FoodViewModel::class.java)
        listPesanan=foodViewModel.getAllFoods()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        //binding
        binding=FragmentKeranjangBinding.inflate(inflater,container,false)
        val recyclerView = binding.rvKeranjang
        listPesanan = foodViewModel.getAllFoods()
        listPesanan.observe(viewLifecycleOwner){item->

            if(item.size==0){
                recyclerView.visibility=View.GONE
                binding.tvTotalHarga.visibility=View.GONE
                binding.tvTotalHargaPesanan.visibility=View.GONE
                binding.btnPesan.visibility=View.GONE
                binding.ivEmptyCart.visibility=View.VISIBLE
                return@observe
            }
            binding.tvTotalHarga.visibility=View.VISIBLE
            binding.tvTotalHargaPesanan.visibility=View.VISIBLE
            binding.btnPesan.visibility=View.VISIBLE
            recyclerView.visibility=View.VISIBLE
            binding.ivEmptyCart.visibility=View.GONE

            var totalPrice:Int = 0
            item.forEach {
                var t =0
                t = it.quantity * it.harga
                totalPrice +=t
            }
            binding.tvTotalHargaPesanan.text="Rp. ${totalPrice.toString()}"
            adapter = KeranjangRecyclerViewAdapter(requireContext(),item,foodViewModel)
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