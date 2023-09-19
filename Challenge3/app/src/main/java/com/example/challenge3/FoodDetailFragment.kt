package com.example.challenge3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Base64DataException
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.challenge3.databinding.FragmentFoodDetailBinding
import com.example.challenge3.models.Food

class FoodDetailFragment : Fragment() {

    private lateinit var binding:FragmentFoodDetailBinding
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
        binding.tvFoodPrice.text=data.Price
        binding.flFoodDetailImage.setBackgroundResource(data.imageId)
        binding.clLokasi.setOnClickListener{
            Log.d("int", "Constraint Layout Di Klik")
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(data.urlLocation))
            startActivity(intent)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}