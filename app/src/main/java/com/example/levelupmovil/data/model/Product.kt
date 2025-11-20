package com.example.levelupmovil.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

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

data class ProductDto(
    val id: Int,
    val name: String,
    val brand: String,
    @SerializedName("imgUrl")
    val imageUrl: String,
    val price: Int,
    val category: String?,
    @SerializedName("product_condition")
    val condition: String
)