package com.example.levelupmovil.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.levelupmovil.data.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<Product>>

    @Query("""
        SELECT * FROM products
        WHERE (:category IS NULL OR category = :category)
        AND (:query IS NULL OR name LIKE '%' || :query || '%')
    """)
    fun getFilteredProducts(query: String?, category: String?): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<Product>)

    @Query("SELECT * FROM products WHERE id = :id")
    fun getProductById(id: Int): Flow<Product>

    @Query("DELETE FROM products WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("UPDATE products SET name = :name, price = :price WHERE id = :id")
    suspend fun updateProductSimple(id: Int, name: String, price: Int)
}