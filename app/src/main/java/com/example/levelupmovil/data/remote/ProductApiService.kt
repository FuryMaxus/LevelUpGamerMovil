package com.example.levelupmovil.data.remote

import com.example.levelupmovil.data.model.ProductDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductApiService {
    @GET("api/v1/productos")
    suspend fun getAllProducts(): List<ProductDto>

    @PUT("api/v1/productos/{id}")
    suspend fun updateProduct(@Path("id") id: Int, @Body product: ProductDto): ProductDto

    @DELETE("api/v1/productos/{id}")
    suspend fun deleteProduct(@Path("id") id: Int): retrofit2.Response<Unit>
}