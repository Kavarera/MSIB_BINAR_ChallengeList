package com.example.challenge3.page

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Base64DataException
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.challenge3.databinding.FragmentFoodDetailBinding
import com.example.challenge3.models.Food
import com.example.challenge3.viewmodels.FoodDetailViewModel

class FoodDetailFragment : Fragment() {

    private lateinit var binding:FragmentFoodDetailBinding
    private val viewModel by viewModels<FoodDetailViewModel>()

    private lateinit var data:Food
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        data = arguments?.getParcelable<Food>("data")!!
        binding = FragmentFoodDetailBinding.inflate(inflater,container,false)



        binding.tvFoodNameDetail.text = data.Name
        binding.tvFooddesc.text=data.description
        binding.tvLokasiDetail.text=data.location
        binding.tvFoodPrice.text="Rp ${data.Price}"
        binding.flFoodDetailImage.setBackgroundResource(data.imageId)
        binding.tvTotalItem.text=viewModel.totalItem.toString()
        binding.tvLokasiDetail.setOnClickListener{
            goGmaps()
        }
        binding.tvLokasi.setOnClickListener{
            goGmaps()
        }

        binding.btnAddItem.setOnClickListener {
            viewModel.addMoreItem()
        }
        binding.btnSubItem.setOnClickListener {
            viewModel.subMoreItem()
        }

        binding.ibBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnAddToCart.text="Masukan Ke Keranjang - Rp, ${data.Price}"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.totalItem.observe(viewLifecycleOwner, Observer { newValue ->
            binding.btnAddToCart.text="Masukan ke Keranjang - Rp. ${newValue * data.Price.toInt()}"
            binding.tvTotalItem.text = newValue.toString()
        })

    }

    private fun goGmaps(){
        Log.d("int", "Constraint Layout Di Klik")
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(this.data.urlLocation))
        startActivity(intent)
    }
}