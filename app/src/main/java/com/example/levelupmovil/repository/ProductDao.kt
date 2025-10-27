package com.example.levelupmovil.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.levelupmovil.model.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    suspend fun getAllProducts(): List<Product>

    @Query("""
        SELECT * FROM products
        WHERE (:category IS NULL OR category = :category)
        AND (:query IS NULL OR name LIKE '%' || :query || '%')
    """)
    suspend fun getFilteredProducts(query: String?, category: String?): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<Product>)
}