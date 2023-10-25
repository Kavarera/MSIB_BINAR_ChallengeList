package com.example.challenge3.pages.FoodDetailPages

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.challenge3.util.viewmodelsfactory.PageViewModelFactory
import com.example.challenge3.databinding.ActivityFoodDetailBinding
import com.example.challenge3.models.Food
import com.example.challenge3.models.FoodKeranjang
import com.example.challenge3.util.ShowSnackbarCustom
import com.example.challenge3.util.networking.ApiRetrofit.ApiClient
import com.example.challenge3.util.viewmodels.FoodDetailViewModel
import com.example.challenge3.util.viewmodels.FoodViewModel

class FoodDetailActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFoodDetailBinding
    private lateinit var foodViewModel: FoodViewModel
    private val foodDetailViewModel by viewModels<FoodDetailViewModel>()
    private var totalitem:Int =1
    private var totalPrice:Int =1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelFactory = PageViewModelFactory(this.application, ApiClient.instance)
        foodViewModel = ViewModelProvider(this,viewModelFactory).get(FoodViewModel::class.java)
        binding = ActivityFoodDetailBinding.inflate(layoutInflater)
        val foodData: Food = intent.getParcelableExtra("data")!!

        binding.tvFoodNameDetail.text = foodData.nama
        binding.tvFooddesc.text=foodData.detail
        binding.tvLokasiDetail.text=foodData.alamatResto
        binding.tvFoodPrice.text=foodData.hargaString
        Glide.with(this)
            .load(foodData.imageUrl)
            .centerCrop()
            .into(binding.ivFoodDetailImage)
//        binding.flFoodDetailImage.setBackgroundResource(foodData.imageId)
        binding.tvTotalItem.text=foodDetailViewModel.totalItem.toString()

        binding.btnAddItem.setOnClickListener {
            foodDetailViewModel.addMoreItem()
        }
        binding.btnSubItem.setOnClickListener {
            foodDetailViewModel.subMoreItem()
        }

        binding.ibBack.setOnClickListener {
            finish()
        }

        binding.btnAddToCart.setOnClickListener {
            val foodKeranjang = FoodKeranjang(
                id = 0,
                foodName = foodData.nama,
                harga = foodData.harga,
                imageUrl = foodData.imageUrl,
                hargaString = foodData.hargaString,
                quantity = totalitem,
                totalPrice = totalPrice,
            )
//            val foodKeranjang = FoodKeranjang(0,foodData.Name,foodData.imageId,foodData.Price.toInt(),
//                totalitem,totalPrice,"No Note"
//            )
            foodViewModel.insertFood(foodKeranjang)

            ShowSnackbarCustom("Berhasil Menambahkan Ke keranjang","Add Success",200,binding.root)
        }

        foodDetailViewModel.totalItem.observe(this){
            item->
            totalitem = item

            totalPrice = totalitem * foodData.harga
            binding.tvTotalItem.text = totalitem.toString()
            binding.btnAddToCart.text = "Tambah Ke Keranjang - Rp. $totalPrice"
        }



        setContentView(binding.root)
    }

}