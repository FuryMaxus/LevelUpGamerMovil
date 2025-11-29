package com.example.levelupmovil.repository

import android.util.Log
import com.example.levelupmovil.data.model.Category
import com.example.levelupmovil.data.model.Product
import com.example.levelupmovil.data.model.ProductCondition
import com.example.levelupmovil.data.model.ProductDto
import com.example.levelupmovil.data.remote.ProductApiService
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class ProductRepository(private val api: ProductApiService,
                        private val dao: ProductDao,
                        private val userPreferences: UserPreferencesRepository
) {

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
        try {
            api.deleteProduct(id)
            dao.deleteById(id)
        } catch (e: Exception) {
            e.printStackTrace()
        }
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
            dao.deleteAll()
            dao.insertAll(entities)

        } catch (e: HttpException) {
            if (e.code() == 401 || e.code() == 403) {
                userPreferences.clearAuthData()
            }
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}