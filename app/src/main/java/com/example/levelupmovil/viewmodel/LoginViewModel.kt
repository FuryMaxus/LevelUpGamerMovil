package com.example.levelupmovil.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImagePainter
import com.example.levelupmovil.model.LoginErrores
import com.example.levelupmovil.model.LoginUiState
import com.example.levelupmovil.repository.UserDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userDataStore: UserDataStore
) : ViewModel() {

    private val _loginData = MutableStateFlow(LoginUiState())

    val loginData: StateFlow<LoginUiState> = _loginData


    fun onEmailChange(newEmail: String){
        _loginData.update { it.copy(email = newEmail, errors = it.errors.copy(email = null)) }
    }

    fun onPasswordChange(newPassword: String){
        _loginData.update { it.copy(password = newPassword, errors = it.errors.copy(password = null)) }
    }



    fun validarFormulario(): Boolean{
        val formularioActual = _loginData.value
        val errors = LoginErrores(
            email = if (!Patterns.EMAIL_ADDRESS.matcher(formularioActual.email).matches()) "Error, el E-mail debe ser válido!" else null,
            password = if (formularioActual.password.length < 6) "Error, la Contraseña debe tener al menos 6 carácteres" else null
        )


        val hayErrores = listOfNotNull(
            errors.email,
            errors.password
        ).isNotEmpty()

        _loginData.update { it.copy(errors = errors) }

        return !hayErrores
    }

    fun tryLogin(onSuccess: () -> Unit){
        if (!validarFormulario()) {
            return
        }
        viewModelScope.launch {
            val savedData = userDataStore.userDataFlow.first()
            val email = _loginData.value.email
            val passwd = _loginData.value.password
            if (email == savedData.email && passwd == savedData.passwordHash){
                onSuccess()
            }else {
                _loginData.update {
                    it.copy(errors = it.errors.copy(email = "Email o contraseña incorrectos"))
                }
            }
        }
    }
}