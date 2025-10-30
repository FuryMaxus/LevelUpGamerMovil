package com.example.levelupmovil.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.levelupmovil.viewmodel.LoginViewModel
import com.example.levelupmovil.viewmodel.UsuarioViewModel


enum class AuthView{
    LOGIN,
    REGISTER
}

@Composable
fun ProfileScreen(
    onLoginSucces: () -> Unit,
    onRegisterSucces: () -> Unit
){
    var currentView by remember { mutableStateOf(AuthView.LOGIN) }

    val loginViewModel: LoginViewModel = viewModel()
    val usuarioViewModel: UsuarioViewModel = viewModel()

    Column(modifier = Modifier.fillMaxSize()) {

        when (currentView) {
            AuthView.LOGIN -> {
                LoginScreen(
                    viewModel = loginViewModel,
                    onLoginSuccess = onLoginSucces,
                    onRegisterClick = { currentView = AuthView.REGISTER }
                )
            }
            AuthView.REGISTER -> {
                RegisterScreen(
                    viewModel = usuarioViewModel,
                    onRegisterSuccess = onRegisterSucces,
                    onLoginClick = { currentView = AuthView.LOGIN }
                )
            }
        }
    }
}