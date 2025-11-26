package com.example.levelupmovil.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.levelupmovil.LevelUpMovilApplication
import com.example.levelupmovil.data.model.LoginErrores
import com.example.levelupmovil.data.model.LoginUiState
import com.example.levelupmovil.repository.AuthRepository
import com.example.levelupmovil.util.EmailValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
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
            email = if (!EmailValidator.isValid(formularioActual.email)) "Error, el E-mail debe ser válido!" else null,
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
            val email = _loginData.value.email
            val password = _loginData.value.password
            val result = authRepository.login(email, password)

            if (result.isSuccess) {
                onSuccess()
            } else {
                val mensajeError = result.exceptionOrNull()?.message ?: "Error de conexión o credenciales"
                _loginData.update {
                    it.copy(errors = it.errors.copy(email = mensajeError))
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LevelUpMovilApplication)
                LoginViewModel(app.container.authRepository)
            }
        }
    }
}