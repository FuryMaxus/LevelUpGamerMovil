package com.example.levelupmovil.data

import android.content.Context
import androidx.room.Room
import com.example.levelupmovil.data.remote.ApiService
import com.example.levelupmovil.repository.AppDataBase
import com.example.levelupmovil.repository.ProductRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(private val context: Context) {

    private val BASE_URL = "http://192.168.1.17:8080/"

    private val productRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val apiService: ApiService by lazy {
        productRetrofit.create(ApiService::class.java)
    }

    private val database: AppDataBase by lazy {
        Room.databaseBuilder(
            context.applicationContext,
            AppDataBase::class.java,
            "levelup.catalog.db"
        ).build()
    }

    val productRepository: ProductRepository by lazy {
        ProductRepository(
            api = apiService,
            dao = database.productDao()
        )
    }
}