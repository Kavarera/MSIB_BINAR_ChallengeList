package com.example.challenge3.page

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge3.R
import com.example.challenge3.adapters.MainMenuRVAdapter
import com.example.challenge3.databinding.FragmentMenuBinding
import com.example.challenge3.models.Food
import com.example.challenge3.models.RecyclerViewOption

class MenuFragment : Fragment() {

    private lateinit var binding:FragmentMenuBinding
    var gridOption = RecyclerViewOption.LINEAR_LAYOUT
    private val foodList = mutableListOf<Food>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        for(i in 1..20){
            val food = Food(
                "Food Name $i", // Nama makanan
                "Description for Food $i", // Deskripsi makanan
                R.color.purple_200, // ID gambar makanan (ganti dengan ID gambar yang sesuai)
                "Location for Food $i" // Lokasi makanan
            )
            foodList.add(food)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentMenuBinding.inflate(inflater,container,false)

        val rv = binding.rvMenuMakanan
        rv.layoutManager = LinearLayoutManager(requireContext())
        var adapter = MainMenuRVAdapter(foodList,RecyclerViewOption.LINEAR_LAYOUT)
        rv.adapter=adapter

        binding.ibGridOption.setOnClickListener {
            if(gridOption==RecyclerViewOption.LINEAR_LAYOUT){
                gridOption=RecyclerViewOption.GRID_LAYOUT
                binding.ibGridOption.setImageResource(R.drawable.ic_menu)
                rv.layoutManager = GridLayoutManager(requireContext(),2)
                adapter = MainMenuRVAdapter(foodList,RecyclerViewOption.GRID_LAYOUT)
                adapter.switchRvMode(RecyclerViewOption.GRID_LAYOUT)
                rv.adapter=adapter
                Toast.makeText(requireContext(),"change to grid",Toast.LENGTH_SHORT).show()
            }
            else{
                gridOption=RecyclerViewOption.LINEAR_LAYOUT
                binding.ibGridOption.setImageResource(R.drawable.ic_list)
                rv.layoutManager = LinearLayoutManager(requireContext())
                adapter = MainMenuRVAdapter(foodList,RecyclerViewOption.LINEAR_LAYOUT)
                adapter.switchRvMode(RecyclerViewOption.LINEAR_LAYOUT)
                rv.adapter=adapter
            }
        }
        return binding.root
    }


}