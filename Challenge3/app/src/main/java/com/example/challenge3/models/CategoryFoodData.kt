package com.example.challenge3.models

import com.google.gson.annotations.SerializedName

data class CategoryFoodData(
    @SerializedName("nama") val nama: String,
    @SerializedName("image_url")val imageUrl: String,
)
