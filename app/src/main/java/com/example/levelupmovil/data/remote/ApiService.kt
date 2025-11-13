package com.example.levelupmovil.data.remote

import com.example.levelupmovil.data.model.Product
import com.example.levelupmovil.data.model.ProductDto
import retrofit2.http.GET

interface ApiService {
    @GET("api/v1/productos")
    suspend fun getAllProducts(): List<ProductDto>
}