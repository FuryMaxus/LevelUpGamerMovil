package com.example.levelupmovil.data

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
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

private val Context.dataStore by preferencesDataStore(name = "user_prefs")
class AppContainer(private val context: Context) {

    private val IP_ADDRESS = "192.168.1.17"

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
    private fun createRetrofit(port: Int): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://$IP_ADDRESS:$port/")
            .client(sharedOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val authApiService: AuthApiService by lazy {
        createRetrofit(8081).create(AuthApiService::class.java)
    }

    val productApiService: ProductApiService by lazy {
        createRetrofit(8080).create(ProductApiService::class.java)
    }


    val productRepository: ProductRepository by lazy {
        ProductRepository(
            api = productApiService,
            dao = database.productDao()
        )
    }

    val authRepository by lazy {
        AuthRepository(authApiService, userPreferencesRepository)
    }
}