package com.example.levelupmovil.data.model



data class LoginUiState(
    val email : String = "",
    val password : String = "",
    val errors : LoginErrores = LoginErrores()

)

data class LoginErrores(
    val email: String? = null,
    val password: String? = null
)