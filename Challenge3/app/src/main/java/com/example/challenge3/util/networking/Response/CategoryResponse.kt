package com.example.challenge3.util.networking.Response

import com.example.challenge3.models.CategoryFoodData

data class CategoryResponse(
	val data: List<CategoryFoodData?>?,
	val message: String,
	val status: Boolean
)



