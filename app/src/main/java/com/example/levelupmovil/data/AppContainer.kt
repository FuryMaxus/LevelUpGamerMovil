package com.example.levelupmovil.data

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.levelupmovil.BuildConfig
import com.example.levelupmovil.data.remote.AuthApiService
import com.example.levelupmovil.data.remote.AuthInterceptor
import com.example.levelupmovil.data.remote.ProductApiService
import com.example.levelupmovil.repository.AppDataBase
import com.example.levelupmovil.repository.AuthRepository
import com.example.levelupmovil.repository.ProductRepository
import com.example.levelupmovil.repository.UserPreferencesRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.getValue
import com.example.levelupmovil.data.remote.ApiConfig

private val Context.dataStore by preferencesDataStore(name = "user_prefs")
class AppContainer(private val context: Context) {

    private val database: AppDataBase by lazy {
        Room.databaseBuilder(
            context.applicationContext,
            AppDataBase::class.java,
            "levelup.catalog.db"
        ).build()
    }

    val userPreferencesRepository by lazy {
        UserPreferencesRepository(context.dataStore)
    }

    private val sharedOkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(userPreferencesRepository))
            .build()
    }
    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(sharedOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val productApiService: ProductApiService by lazy {
        createRetrofit(ApiConfig.PRODUCT_BASE_URL).create(ProductApiService::class.java)
    }

    val authApiService: AuthApiService by lazy {
        createRetrofit(ApiConfig.AUTH_BASE_URL).create(AuthApiService::class.java)
    }

    val productRepository: ProductRepository by lazy {
        ProductRepository(
            api = productApiService,
            dao = database.productDao(),
            userPreferences = userPreferencesRepository
        )
    }

    val authRepository by lazy {
        AuthRepository(authApiService, userPreferencesRepository)
    }
}