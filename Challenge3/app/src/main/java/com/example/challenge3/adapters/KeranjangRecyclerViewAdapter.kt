package com.example.challenge3.adapters

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.challenge3.ViewModelFactory
import com.example.challenge3.databinding.FoodLinearBinding
import com.example.challenge3.databinding.ItemPesananKeranjangBinding
import com.example.challenge3.models.Food
import com.example.challenge3.models.FoodKeranjang
import com.example.challenge3.viewmodels.FoodViewModel


class KeranjangRecyclerViewAdapter(private val listFood:List<FoodKeranjang>,private val foodViewModel:FoodViewModel): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemPesananKeranjangBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LinearPesananKeranjangHolder(binding, foodViewModel)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val food = listFood[position]
        Log.d("keranjang","binding ${food.foodName}")
        (holder as LinearPesananKeranjangHolder).bind(food)
        Log.d("keranjang","${food.foodName} is binded")
    }

    override fun getItemCount(): Int {
        return listFood.size
    }
    inner class LinearPesananKeranjangHolder(private val binding: ItemPesananKeranjangBinding,private val foodViewModel: FoodViewModel):RecyclerView.ViewHolder(binding.root) {
        fun bind(item:FoodKeranjang){
            binding.ivImagePesananKeranjang.setImageResource(item.imageID)
            binding.tvNamaPesananKeranjang.text = item.foodName
            binding.tvHargaPesananKeranjang.text=item.foodPrice.toString()
            binding.tvTotalItemPesananKeranjang.text=item.quantity.toString()
            binding.etCatatanItemPesananKeranjang.setText(item.catatan)
            binding.ivRemove.setOnClickListener {
                removeItem(adapterPosition);
                Log.d("keranjang","running delete keranjang")
            }
            binding.btnMinItemKeranjang.setOnClickListener {
                if(item.quantity>1){
                    foodViewModel.updateQuantityandPrice(item.quantity-1,item)
                    notifyItemChanged(adapterPosition)
                }
            }
            binding.btnAddItemKeranjang.setOnClickListener {
                foodViewModel.updateQuantityandPrice(item.quantity+1,item)
                notifyItemChanged(adapterPosition)
            }
        }
        private fun removeItem(position:Int){
            if(position!=RecyclerView.NO_POSITION){
                foodViewModel.deleteById(listFood[position].id)
                Log.d("keranjang","deleting in adapter ${listFood[position]}")
            }
        }
        private fun subtractTotalQuantity(oldValue:Int,foodKeranjang: FoodKeranjang){
            foodViewModel.updateQuantityandPrice(oldValue-1,foodKeranjang)
        }
    }
}