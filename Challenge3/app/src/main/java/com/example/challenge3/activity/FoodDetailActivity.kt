package com.example.challenge3.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.challenge3.ViewModelFactory
import com.example.challenge3.databinding.ActivityFoodDetailBinding
import com.example.challenge3.models.Food
import com.example.challenge3.models.FoodKeranjang
import com.example.challenge3.viewmodels.FoodDetailViewModel
import com.example.challenge3.viewmodels.FoodViewModel

class FoodDetailActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFoodDetailBinding
    private lateinit var foodViewModel:FoodViewModel
    private val foodDetailViewModel by viewModels<FoodDetailViewModel>()
    private var totalitem:Int =1
    private var totalPrice:Int =1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelFactory = ViewModelFactory(this.application)
        foodViewModel = ViewModelProvider(this,viewModelFactory).get(FoodViewModel::class.java)
        binding = ActivityFoodDetailBinding.inflate(layoutInflater)
        val foodData: Food = intent.getParcelableExtra("foodData")!!

        binding.tvFoodNameDetail.text = foodData.Name
        binding.tvFooddesc.text=foodData.description
        binding.tvLokasiDetail.text=foodData.location
        binding.tvFoodPrice.text="Rp ${foodData.Price}"
        binding.flFoodDetailImage.setBackgroundResource(foodData.imageId)
        binding.tvTotalItem.text=foodDetailViewModel.totalItem.toString()
        binding.tvLokasiDetail.setOnClickListener{
            goGmaps(foodData)
        }
        binding.tvLokasi.setOnClickListener{
            goGmaps(foodData)
        }

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
            val foodKeranjang = FoodKeranjang(0,foodData.Name,foodData.imageId,foodData.Price.toInt(),
                totalitem,totalPrice,"No Note"
            )
            foodViewModel.insertFood(foodKeranjang)

            Toast.makeText(this,"Berhasil Menambahkan ke keranjang",Toast.LENGTH_SHORT).show()
        }

        foodDetailViewModel.totalItem.observe(this){
            item->
            totalitem = item
            totalPrice = totalitem * foodData.Price.toInt()
            binding.tvTotalItem.text = totalitem.toString()
            binding.btnAddToCart.text = "Tambah Ke Keranjang - Rp. $totalPrice"
        }



        setContentView(binding.root)
    }

    private fun goGmaps(foodData:Food){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(foodData.urlLocation))
        startActivity(intent)
    }
}