package com.example.challenge3.page

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge3.R
import com.example.challenge3.adapters.MainMenuRVAdapter
import com.example.challenge3.databinding.FragmentMenuBinding
import com.example.challenge3.models.Food
import com.example.challenge3.models.RecyclerViewOption
import kotlin.random.Random

class MenuFragment : Fragment() {

    private lateinit var binding:FragmentMenuBinding
    var gridOption = RecyclerViewOption.LINEAR_LAYOUT
    private val foodList = mutableListOf<Food>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        for(a in 1..20){

            val food = Food(
                "Food Name $a", // Nama makanan
                "Description for Food $a", // Deskripsi makanan
                resources.obtainTypedArray(R.array.foto_makanan).getResourceId(Random.Default.nextInt(1,13),0), // ID gambar makanan (ganti dengan ID gambar yang sesuai)
                "${Random.Default.nextInt(5000,50000)}",
                "St. Somewhere number 123, Click for more",
                resources.obtainTypedArray(R.array.gmaps_url).getString(Random.Default.nextInt(1,6)).toString()// Lokasi makanan
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
        adapter.setOnItemClickCallback(object : MainMenuRVAdapter.IonItemClickCallback{
            override fun onItemClicked(data: Food) {
                val mb = Bundle().apply {
                    putParcelable("data",data)
                }
//                Log.d("df2","Kirim data food ${data}")
                findNavController().navigate(R.id.action_menuFragment_to_foodDetailFragment,mb)
            }

        })
        rv.adapter=adapter

        binding.ibGridOption.setOnClickListener {
            if(gridOption==RecyclerViewOption.LINEAR_LAYOUT){
                gridOption=RecyclerViewOption.GRID_LAYOUT
                binding.ibGridOption.setImageResource(R.drawable.ic_menu)
                rv.layoutManager = GridLayoutManager(requireContext(),2)
                adapter = MainMenuRVAdapter(foodList,RecyclerViewOption.GRID_LAYOUT)
                adapter.switchRvMode(RecyclerViewOption.GRID_LAYOUT)
                adapter.setOnItemClickCallback(object : MainMenuRVAdapter.IonItemClickCallback{
                    override fun onItemClicked(data: Food) {
                        val mb = Bundle().apply {
                            putParcelable("data",data)
                        }
                        it.findNavController().navigate(R.id.action_menuFragment_to_foodDetailFragment,mb)
                    }

                })
                rv.adapter=adapter
                Toast.makeText(requireContext(),"change to grid",Toast.LENGTH_SHORT).show()
            }
            else{
                gridOption=RecyclerViewOption.LINEAR_LAYOUT
                binding.ibGridOption.setImageResource(R.drawable.ic_list)
                rv.layoutManager = LinearLayoutManager(requireContext())
                adapter = MainMenuRVAdapter(foodList,RecyclerViewOption.LINEAR_LAYOUT)
                adapter.switchRvMode(RecyclerViewOption.LINEAR_LAYOUT)
                adapter.setOnItemClickCallback(object : MainMenuRVAdapter.IonItemClickCallback{
                    override fun onItemClicked(data: Food) {
                        val mb = Bundle().apply {
                            putParcelable("data",data)
                        }
                        it.findNavController().navigate(R.id.action_menuFragment_to_foodDetailFragment,mb)
                    }

                })
                rv.adapter=adapter
            }
        }
        binding.incl1.incl11.ivItem.setImageResource(R.drawable.ayam2)
        binding.incl1.incl11.tvNamaFood.text="Ayam"

        binding.incl1.incl12.ivItem.setImageResource(R.drawable.burger)
        binding.incl1.incl12.tvNamaFood.text="Burger"

        binding.incl1.incl13.ivItem.setImageResource(R.drawable.dimsum)
        binding.incl1.incl13.tvNamaFood.text="Dimsum"

        binding.incl1.incl14.ivItem.setImageResource(R.drawable.eskrim)
        binding.incl1.incl14.tvNamaFood.text="Es Krim"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}