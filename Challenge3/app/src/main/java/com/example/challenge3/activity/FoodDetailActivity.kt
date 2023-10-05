package com.example.challenge3.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navArgs
import com.example.challenge3.databinding.ActivityFoodDetailBinding
import com.example.challenge3.models.Food

class FoodDetailActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFoodDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodDetailBinding.inflate(layoutInflater)
        val foodData: Food = intent.getParcelableExtra("foodData")!!

        binding.tvFoodNameDetail.text = foodData.Name
        binding.tvFooddesc.text=foodData.description
        binding.tvLokasiDetail.text=foodData.location
        binding.tvFoodPrice.text="Rp ${foodData.Price}"
        binding.flFoodDetailImage.setBackgroundResource(foodData.imageId)
        binding.tvTotalItem.text="1"//viewModel.totalItem.toString()
        binding.tvLokasiDetail.setOnClickListener{
            goGmaps(foodData)
        }
        binding.tvLokasi.setOnClickListener{
            goGmaps(foodData)
        }

        binding.btnAddItem.setOnClickListener {
            //viewModel.addMoreItem()
        }
        binding.btnSubItem.setOnClickListener {
            //viewModel.subMoreItem()
        }

        binding.ibBack.setOnClickListener {
            finish()
        }



        setContentView(binding.root)
    }

    private fun goGmaps(foodData:Food){
        Log.d("int", "Constraint Layout Di Klik")
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(foodData.urlLocation))
        startActivity(intent)
    }
}