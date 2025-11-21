package com.example.levelupmovil.data.remote

data class UserInfoResponse(
    val id: Int,
    val name: String,
    val email: String,
    val address: String?
)