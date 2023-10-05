package com.example.challenge3.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.challenge3.databinding.FoodGridBinding
import com.example.challenge3.databinding.FoodLinearBinding
import com.example.challenge3.models.Food
import com.example.challenge3.models.RecyclerViewOption

class MainMenuRVAdapter(private var foods: List<Food>,layoutManager:RecyclerViewOption):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var onItemClickCallback:IonItemClickCallback
    interface IonItemClickCallback {
        fun onItemClicked(data: Food)
    }

    inner class MainMenuLinearHolder(private val binding: FoodLinearBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item:Food){
            binding.ivImage.setImageResource(item.imageId)
            binding.tvNamaFood.text=item.Name
            binding.tvHarga.text=item.Price
            Log.e("ui","apply ui in linearHolder")
        }
    }
    inner class MainMenuGridHolder(private val binding:FoodGridBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:Food){
            binding.tvGimage.setImageResource(item.imageId)
            binding.tvGnamaFood.text=item.Name
            binding.tvGharga.text=item.Price
            Log.e("ui","apply ui in gridHolder")
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
        return if (rvMode==RecyclerViewOption.LINEAR_LAYOUT) {
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