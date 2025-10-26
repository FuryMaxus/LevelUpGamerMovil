package com.example.levelupmovil.model

data class Product(
    val id: Int,
    val name: String,
    val brand: String,
    val price: Int,
    val category: Category,
    val condition: ProductCondition,
    val imageUrl: String,

)
