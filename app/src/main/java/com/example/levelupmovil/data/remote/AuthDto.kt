package com.example.levelupmovil.data.remote

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val jwt: String
)

data class RegisterRequest(
    val username: String,
    val password: String,
    val email: String
)
