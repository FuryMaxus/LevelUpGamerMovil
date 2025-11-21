package com.example.levelupmovil.data.remote

import com.example.levelupmovil.data.model.ProductDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductApiService {
    @GET("api/v1/productos")
    suspend fun getAllProducts(): List<ProductDto>
}