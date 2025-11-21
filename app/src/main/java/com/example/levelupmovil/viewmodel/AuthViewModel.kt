package com.example.levelupmovil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.levelupmovil.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.example.levelupmovil.LevelUpMovilApplication

class AuthViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val isLoggedIn: StateFlow<Boolean> = userPreferencesRepository.authToken
        .map { token -> !token.isNullOrBlank() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )


    fun onLogout() {
        viewModelScope.launch {
            userPreferencesRepository.clearAuthData()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LevelUpMovilApplication)
                AuthViewModel(app.container.userPreferencesRepository)
            }
        }
    }

}