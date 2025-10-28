package com.example.levelupmovil.model


//representamos el estado del formulario
data class LoginUiState(
    val email : String = "",
    val password : String = "",
    val errors : LoginErrores = LoginErrores() //contendra errores

)


//almacenamos posibles errores
data class LoginErrores(
    val email: String? = null,
    val password: String? = null
)