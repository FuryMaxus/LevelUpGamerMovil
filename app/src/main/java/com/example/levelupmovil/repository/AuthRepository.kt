package com.example.levelupmovil.repository

import com.example.levelupmovil.data.remote.AuthApiService
import com.example.levelupmovil.data.remote.LoginRequest
import com.example.levelupmovil.data.remote.RegisterRequest

class AuthRepository(
    private val api: AuthApiService,
    private val userPreferences: UserPreferencesRepository
) {

    suspend fun login(email: String, pass: String): Result<Boolean> {
        return try {

            val response = api.login(LoginRequest(email, pass))

            userPreferences.saveToken(response.jwt)

            val userProfile = api.getMyProfile()

            userPreferences.saveAuthData(
                token = response.jwt,
                name = userProfile.name,
                email = userProfile.email,
                address = userProfile.address
            )

            Result.success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun register(name: String, email: String, pass: String, address: String): Result<Boolean> {
        return try {
            api.register(
                RegisterRequest(
                    username = name,
                    password = pass,
                    email = email,
                    address = address
                )
            )
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