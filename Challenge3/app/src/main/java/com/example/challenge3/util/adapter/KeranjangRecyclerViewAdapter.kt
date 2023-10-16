package com.example.challenge3.util.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.challenge3.databinding.ItemPesananKeranjangBinding
import com.example.challenge3.models.FoodKeranjang
import com.example.challenge3.util.viewmodels.FoodViewModel


class KeranjangRecyclerViewAdapter(private val listFood:List<FoodKeranjang>,private val foodViewModel: FoodViewModel): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemPesananKeranjangBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LinearPesananKeranjangHolder(binding, foodViewModel)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val food = listFood[position]
        (holder as LinearPesananKeranjangHolder).bind(food)
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
            }
        }
        private fun subtractTotalQuantity(oldValue:Int,foodKeranjang: FoodKeranjang){
            foodViewModel.updateQuantityandPrice(oldValue-1,foodKeranjang)
        }
    }
}