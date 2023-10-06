package com.example.challenge3.page

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge3.R
import com.example.challenge3.adapters.MainMenuRVAdapter
import com.example.challenge3.databinding.FragmentMenuBinding
import com.example.challenge3.helper.PreferencesHelper
import com.example.challenge3.models.Food
import com.example.challenge3.models.RecyclerViewOption
import com.example.challenge3.viewmodels.MainActivityViewModel
import kotlinx.coroutines.launch
import kotlin.random.Random

class MenuFragment : Fragment() {
    //viewmodel
//    private lateinit var sharedViewModel:MainActivityViewModel


    private lateinit var binding:FragmentMenuBinding
    private var gridOption:Int = 0
//    var gridOption = RecyclerViewOption.LINEAR_LAYOUT.value
    private lateinit var foodListt: LiveData<List<Food>>
    private var foodList = mutableListOf<Food>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        for(a in 1..20){
            val food = Food(
                a,
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
        val mySharedPreferences =  PreferencesHelper.getInstance(requireContext())
        gridOption = mySharedPreferences.layoutOption
        rv.layoutManager = if(gridOption==RecyclerViewOption.LINEAR_LAYOUT.value){
            LinearLayoutManager(requireContext())
        } else{
            GridLayoutManager(requireContext(),2)
        }
        var adapter = if(gridOption==RecyclerViewOption.LINEAR_LAYOUT.value){
            MainMenuRVAdapter(foodList,RecyclerViewOption.LINEAR_LAYOUT)
        } else{
            MainMenuRVAdapter(foodList,RecyclerViewOption.GRID_LAYOUT)

        }

//            MainMenuRVAdapter(foodList,RecyclerViewOption.LINEAR_LAYOUT)
        adapter.setOnItemClickCallback(object : MainMenuRVAdapter.IonItemClickCallback{
            override fun onItemClicked(data: Food) {
//                val mb = Bundle().apply {
//                    putParcelable("data",data)
//                }
                val mb = bundleOf("foodData" to data)
//                Log.d("df2","Kirim data food ${data}")
                findNavController().navigate(R.id.action_menuFragment_to_foodDetailActivity,mb)
            }

        })
        rv.adapter=adapter

        binding.ibGridOption.setOnClickListener {
            if(gridOption==RecyclerViewOption.LINEAR_LAYOUT.value){
                gridOption=RecyclerViewOption.GRID_LAYOUT.value
                mySharedPreferences.layoutOption=gridOption
                binding.ibGridOption.setImageResource(R.drawable.ic_menu)
                rv.layoutManager = GridLayoutManager(requireContext(),2)
                adapter = MainMenuRVAdapter(foodList,RecyclerViewOption.GRID_LAYOUT)
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
                gridOption=RecyclerViewOption.LINEAR_LAYOUT.value
                mySharedPreferences.layoutOption = gridOption
                binding.ibGridOption.setImageResource(R.drawable.ic_list)
                rv.layoutManager = LinearLayoutManager(requireContext())
                adapter = MainMenuRVAdapter(foodList,RecyclerViewOption.LINEAR_LAYOUT)
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
        binding.incl1.incl11.ivItem.setImageResource(R.color.black)
        binding.incl1.incl11.tvNamaFood.text="Ayam"

        binding.incl1.incl12.ivItem.setImageResource(R.color.black)
        binding.incl1.incl12.tvNamaFood.text="Burger"

        binding.incl1.incl13.ivItem.setImageResource(R.color.black)
        binding.incl1.incl13.tvNamaFood.text="Dimsum"

        binding.incl1.incl14.ivItem.setImageResource(R.color.black)
        binding.incl1.incl14.tvNamaFood.text="Es Krim"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }



}