package com.example.challenge3.util.networking.Request

data class OrderRequest(
	val total: Int,
	val orders: List<OrdersItem>?,
	val username: String
)

data class OrdersItem(
	val nama: String,
	val harga: Int,
	val qty: Int,
	val catatan: String
)

