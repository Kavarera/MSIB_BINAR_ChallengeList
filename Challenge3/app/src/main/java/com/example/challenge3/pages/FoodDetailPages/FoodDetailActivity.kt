package com.example.challenge3.pages.FoodDetailPages

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.challenge3.databinding.ActivityFoodDetailBinding
import com.example.challenge3.models.Food
import com.example.challenge3.models.FoodKeranjang
import com.example.challenge3.util.ShowSnackbarCustom
import com.example.challenge3.util.viewmodels.FoodDetailViewModel
import com.example.challenge3.util.viewmodels.KeranjangViewModel
import org.koin.android.ext.android.inject

class FoodDetailActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFoodDetailBinding
    private val foodViewModel: KeranjangViewModel by inject()
    private val foodDetailViewModel:FoodDetailViewModel by inject()
    private var totalitem:Int =1
    private var totalPrice:Int =1
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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