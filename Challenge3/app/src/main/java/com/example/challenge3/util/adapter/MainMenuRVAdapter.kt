package com.example.challenge3.util.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challenge3.databinding.FoodGridBinding
import com.example.challenge3.databinding.FoodLinearBinding
import com.example.challenge3.models.Food
import com.example.challenge3.models.enumclass.EnumRecyclerViewOption


class MainMenuRVAdapter(private var c:Context,private var foods: List<Food>,layoutManager: EnumRecyclerViewOption):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface IonItemClickCallback {
        fun onItemClicked(data: Food)
    }

    private lateinit var onItemClickCallback: IonItemClickCallback

    inner class MainMenuLinearHolder(private val binding: FoodLinearBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item:Food){
            Glide.with(c)
                .load(item.imageUrl)
                .into(binding.ivImage)
            binding.tvNamaFood.text=item.nama
            binding.tvHarga.text=item.hargaString
        }
    }
    inner class MainMenuGridHolder(private val binding:FoodGridBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:Food){
            Glide.with(c)
                .load(item.imageUrl)
                .into(binding.tvGimage)
//            binding.tvGimage.setImageResource(item.imageId)
            binding.tvGnamaFood.text=item.nama
            binding.tvGharga.text=item.hargaString
        }
    }

    private var rvMode = layoutManager

    fun setOnItemClickCallback(onItemClickCallback: IonItemClickCallback){
        this.onItemClickCallback=onItemClickCallback
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(foods[holder.adapterPosition])
        }
        when (holder.itemViewType) {
            VIEW_TYPE_LINEAR -> {

                val item = foods[position] as Food
                (holder as MainMenuLinearHolder).bind(item)
            }
            VIEW_TYPE_GRID -> {

                val item = foods[position] as Food
                (holder as MainMenuGridHolder).bind(item)
            }
        }

    }

    override fun getItemCount(): Int {
        return foods.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            VIEW_TYPE_LINEAR -> {
                val binding = FoodLinearBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return MainMenuLinearHolder(binding)
            }
            VIEW_TYPE_GRID -> {
                val binding = FoodGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return MainMenuGridHolder(binding)
            }
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (rvMode== EnumRecyclerViewOption.LINEAR_LAYOUT) {
            VIEW_TYPE_LINEAR
        } else {
            VIEW_TYPE_GRID
        }
    }

    companion object {
        private const val VIEW_TYPE_LINEAR = 0
        private const val VIEW_TYPE_GRID = 1
    }
}