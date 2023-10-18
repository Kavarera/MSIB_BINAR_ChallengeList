package com.example.challenge3.util.networking.Response

import com.example.challenge3.models.Food

data class FoodResponse(
	val data: List<Food>,
	val code:Int,
	val message: String,
	val status: Boolean
)



