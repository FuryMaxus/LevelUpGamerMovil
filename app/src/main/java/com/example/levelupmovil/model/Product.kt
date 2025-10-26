package com.example.levelupmovil.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName= "products")
data class Product(
    @PrimaryKey val id: Int,
    val name: String,
    val brand: String,
    val price: Int,
    val category: Category,
    val condition: ProductCondition,
    val imageUrl: String,

    )
