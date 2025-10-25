package com.example.levelupmovil.model

data class Product(
    val id: Int,
    val name: String,
    val brand: String,
    val price: Double,
    val category: Category,
    val imageUrl: String
)
