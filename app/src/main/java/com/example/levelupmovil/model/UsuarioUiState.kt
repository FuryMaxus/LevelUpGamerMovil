package com.example.levelupmovil.model


//representa el estado del form
data class UsuarioUiState(
    val name: String = "",
    val email : String = "",
    val password: String = "",
    val aceptaTerminos: Boolean = false,
    val errors: UsuarioErrores = UsuarioErrores() //aqui contendra los errores por campo

)


//aqui se almacenan los posibles errores
data class UsuarioErrores(
    val name: String? = null,
    val email: String? = null,
    val password: String? = null
)