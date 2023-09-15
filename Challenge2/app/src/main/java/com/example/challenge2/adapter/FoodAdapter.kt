package com.example.challenge2.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ScrollCaptureCallback
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.challenge2.R
import com.example.challenge2.models.ItemMakanan
import com.example.challenge2.models.RecyclerViewLayoutOption

class FoodAdapter(private var foods: List<ItemMakanan>, lm:RecyclerViewLayoutOption ):RecyclerView.Adapter<FoodAdapter.FoodHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    var currentLayoutManager:RecyclerViewLayoutOption = lm
    interface OnItemClickCallback {
        fun onItemClicked(data:ItemMakanan)

    }
    fun changeLayoutManager(typeMenu:RecyclerViewLayoutOption) {
        currentLayoutManager=typeMenu
        notifyDataSetChanged()
        Log.w("Layout","Layout berubah ke ${typeMenu.name}")
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback=onItemClickCallback
    }

    class FoodHolder(view: View):RecyclerView.ViewHolder(view){
        val image:ImageView=view.findViewById(R.id.imageMakanan)
        val tvName:TextView=view.findViewById(R.id.tvNama)
        val tvHarga:TextView=view.findViewById(R.id.tvHarga)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHolder {
        val layoutItemMakanan = when(currentLayoutManager){
            RecyclerViewLayoutOption.GRID_LAYOUT->R.layout.item_makanan
            RecyclerViewLayoutOption.LINEAR_LAYOUT-> R.layout.item_makanan_linear
        }
        return FoodHolder(LayoutInflater.from(parent.context).inflate(layoutItemMakanan,parent,false))
    }

    override fun onBindViewHolder(holder: FoodHolder, position: Int) {
        //val item = foods[position]
        holder.image.setImageResource(foods[position].image)
        holder.tvName.text=foods[position].name
        holder.tvHarga.text="Rp. ${foods[position].harga}"
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(foods[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return foods.size
    }
}