package com.example.levelupmovil.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApiService {
    @Headers("No-Authentication: true")
    @POST("api/v1/auth/ingreso")
    suspend fun login(@Body request: LoginRequest): LoginResponse
    @Headers("No-Authentication: true")
    @POST("api/v1/auth/registro")
    suspend fun register(@Body request: RegisterRequest): UserInfoResponse
    @GET("api/v1/usuarios/me")
    suspend fun getMyProfile(): UserInfoResponse
}