package com.example.levelupmovil.model


data class UsuarioUiState(
    val name: String = "",
    val email : String = "",
    val password: String = "",
    val aceptaTerminos: Boolean = false,
    val errors: UsuarioErrores = UsuarioErrores()

)

data class UsuarioErrores(
    val name: String? = null,
    val email: String? = null,
    val password: String? = null
)