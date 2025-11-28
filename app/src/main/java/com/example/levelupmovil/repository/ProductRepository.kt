package com.example.levelupmovil.repository

import com.example.levelupmovil.data.model.Category
import com.example.levelupmovil.data.model.Product
import com.example.levelupmovil.data.model.ProductCondition
import com.example.levelupmovil.data.model.ProductDto
import com.example.levelupmovil.data.remote.ProductApiService
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val api: ProductApiService,
                        private val dao: ProductDao) {

    val products: Flow<List<Product>> = dao.getAllProducts()

    fun getFilteredProducts(query: String?, category: String?): Flow<List<Product>> {
        return dao.getFilteredProducts(query, category)
    }

    fun getProductById(id: Int): Flow<Product> = dao.getProductById(id)

    suspend fun updateProduct(id: Int, name: String, price: Int, currentProduct: Product) {
        val dtoToSend = ProductDto(
            id = id,
            name = name,
            price = price,
            brand = currentProduct.brand,
            imageUrl = currentProduct.imageUrl,
            category = currentProduct.category.dbValue,
            condition = currentProduct.condition.dbValue
        )
        api.updateProduct(id, dtoToSend)
        dao.updateProductSimple(id, name, price)
    }

    suspend fun deleteProduct(id: Int) {
        api.deleteProduct(id)
        dao.deleteById(id)
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