package com.example.levelupmovil.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupmovil.model.UserData
import com.example.levelupmovil.model.UsuarioErrores
import com.example.levelupmovil.model.UsuarioUiState
import com.example.levelupmovil.repository.UserDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userDataStore: UserDataStore
) : ViewModel() {
    private val _estado = MutableStateFlow(UsuarioUiState())

    val estado: StateFlow<UsuarioUiState> = _estado




    fun onNameChange(newName: String){
        _estado.update { it.copy(name = newName, errors = it.errors.copy(name = null)) }
    }

    fun onEmailChange(newEmail: String){
        _estado.update { it.copy(email = newEmail, errors = it.errors.copy(email = null)) }
    }

    fun onPasswordChange(newPassword: String){
        _estado.update { it.copy(password = newPassword, errors = it.errors.copy(password = null)) }
    }

    fun onAceptaTerminosChange(newAceptaTerminos: Boolean){
        _estado.update { it.copy(aceptaTerminos = newAceptaTerminos) }
    }


    fun validarFormulario(): Boolean {
        val formularioActual = _estado.value
        val errors = UsuarioErrores(
            name = if (formularioActual.name.isBlank()) "Error, Campo Obligatorio!" else null,
            email = if (!Patterns.EMAIL_ADDRESS.matcher(formularioActual.email)
                    .matches()
            ) "Error, El E-mail debe ser válido!" else null,
            password = if (formularioActual.password.length < 6) "Error, La Contraseña debe tener al menos 6 carácteres!" else null
        )



        val hayErrores = listOfNotNull(
            errors.name,
            errors.password
        ).isNotEmpty()

        _estado.update { it.copy(errors = errors) }

        return !hayErrores

    }

    fun registrarUsuario(onSuccess: () -> Unit) {
        if (validarFormulario() && _estado.value.aceptaTerminos) {
            viewModelScope.launch {
                val datosUsuario = UserData(
                    name = _estado.value.name,
                    email = _estado.value.email,
                    password = _estado.value.password,
                    profilePicUri = ""
                )
                userDataStore.saveUserData(datosUsuario)
                onSuccess()
            }
        }
    }


}