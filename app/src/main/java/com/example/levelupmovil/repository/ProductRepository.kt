package com.example.levelupmovil.repository

import com.example.levelupmovil.data.model.Category
import com.example.levelupmovil.data.model.Product
import com.example.levelupmovil.data.model.ProductCondition
import com.example.levelupmovil.data.remote.ApiService
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val api: ApiService,
                        private val dao: ProductDao) {

    val products: Flow<List<Product>> = dao.getAllProducts()

    fun getFilteredProducts(query: String?, category: String?): Flow<List<Product>> {
        return dao.getFilteredProducts(query, category)
    }

    suspend fun refreshProducts() {
        try {
            val apiResponse = api.getAllProducts()
            val entities = apiResponse.map { dto ->
                Product(
                    id = dto.id,
                    name = dto.name,
                    brand = dto.brand,
                    imageUrl = dto.imageUrl,
                    category = Category.fromDbValue(dto.category),
                    condition = ProductCondition.fromDbValue(dto.condition),
                    price = dto.price
                )
            }
            dao.insertAll(entities)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}