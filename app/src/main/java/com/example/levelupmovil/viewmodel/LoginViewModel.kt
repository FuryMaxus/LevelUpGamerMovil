package com.example.levelupmovil.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.levelupmovil.model.LoginErrores
import com.example.levelupmovil.model.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    private val _estado = MutableStateFlow(LoginUiState())

    val estado: StateFlow<LoginUiState> = _estado


    fun onEmailChange(newEmail: String){
        _estado.update { it.copy(email = newEmail, errors = it.errors.copy(email = null)) }
    }

    fun onPasswordChange(newPassword: String){
        _estado.update { it.copy(password = newPassword, errors = it.errors.copy(password = null)) }
    }



    fun validarFormulario(): Boolean{
        val formularioActual = _estado.value
        val errors = LoginErrores(
            email = if (!Patterns.EMAIL_ADDRESS.matcher(formularioActual.email).matches()) "Error, el E-mail debe ser válido!" else null,
            password = if (formularioActual.password.length < 6) "Error, la Contraseña debe tener al menos 6 carácteres" else null
        )


        val hayErrores = listOfNotNull(
            errors.email,
            errors.password
        ).isNotEmpty()

        _estado.update { it.copy(errors = errors) }

        return !hayErrores
    }
}