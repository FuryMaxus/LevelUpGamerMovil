package com.example.levelupmovil.repository

import com.example.levelupmovil.data.remote.AuthApiService
import com.example.levelupmovil.data.remote.ProductApiService
import com.example.levelupmovil.data.remote.LoginRequest
import com.example.levelupmovil.data.remote.RegisterRequest

class AuthRepository(
    private val api: AuthApiService,
    private val userPreferences: UserPreferencesRepository
) {

    suspend fun login(email: String, pass: String): Result<Boolean> {
        return try {

            val response = api.login(LoginRequest(email, pass))
            val userInfo = api.getMyProfile()

            userPreferences.saveAuthData(email = userInfo.email, name = userInfo.name, token = response.jwt)
            Result.success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun register(name: String, email: String, pass: String): Result<Boolean> {
        return try {
            api.register(RegisterRequest(username = name, password = pass, email = email))
            login(email, pass)

        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun logout() {
        userPreferences.clearAuthData()
    }
}