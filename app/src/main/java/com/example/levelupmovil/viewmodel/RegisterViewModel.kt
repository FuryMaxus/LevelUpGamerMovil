package com.example.levelupmovil.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.levelupmovil.LevelUpMovilApplication
import com.example.levelupmovil.data.model.UserData
import com.example.levelupmovil.data.model.UsuarioErrores
import com.example.levelupmovil.data.model.UsuarioUiState
import com.example.levelupmovil.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _estado = MutableStateFlow(UsuarioUiState())

    val estado: StateFlow<UsuarioUiState> = _estado

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage


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
                _isLoading.value = true
                _errorMessage.value = null

                val result = authRepository.register(
                    name = _estado.value.name,
                    email = _estado.value.email,
                    pass = _estado.value.password
                )
                _isLoading.value = false

                if (result.isSuccess) {
                    onSuccess()
                } else {
                    _errorMessage.value = result.exceptionOrNull()?.message ?: "Error al registrar usuario"
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LevelUpMovilApplication)
                RegisterViewModel(app.container.authRepository)
            }
        }
    }

}