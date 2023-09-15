package com.example.challenge2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.challenge2.models.ItemMakanan

class DetailFoodPage : AppCompatActivity() {
    private lateinit var food:ItemMakanan
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_food_page)

        food = intent.getParcelableExtra("data")!!
        findViewById<ImageView>(R.id.iv_foodImage).setImageResource(food.image)
        findViewById<TextView>(R.id.tv_foodName).text=food.name
        findViewById<TextView>(R.id.tv_foodPrice).text = "Rp. ${food.harga}"
    }
}