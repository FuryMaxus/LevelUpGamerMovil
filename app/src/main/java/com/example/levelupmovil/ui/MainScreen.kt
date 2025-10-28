package com.example.levelupmovil.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.levelupmovil.navigation.AppRoute
import com.example.levelupmovil.navigation.NavigationEvent
import com.example.levelupmovil.ui.components.BottomBar
import com.example.levelupmovil.ui.screens.RegisterScreen
import com.example.levelupmovil.viewmodel.MainViewModel
import com.example.levelupmovil.viewmodel.UsuarioViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val viewModel: MainViewModel = viewModel()
    val navController = rememberNavController()
    val usuarioViewModel: UsuarioViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.navEvents.collect{ event ->
            when(event) {
                is NavigationEvent.NavigateTo -> {
                    navController.navigate(event.appRoute.route) {
                        launchSingleTop=event.singleTop
                        restoreState=true
                        event.popRoute?.let {
                            popUpTo(it.route) {
                                inclusive = event.inclusive
                                saveState = true
                            }
                        }

                    }
                }
                NavigationEvent.NavigateUp -> navController.navigateUp()
                NavigationEvent.PopBackStack -> navController.popBackStack()
            }
        }
    }

    Scaffold(
        bottomBar = {
            BottomBar{route ->
                viewModel.navigateTo(route,singleTop=true,popRoute = AppRoute.Home)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppRoute.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(AppRoute.Home.route) {
                Text("Pantalla inicio")
            }
            composable(AppRoute.Profile.route) {
                Text("Pantalla Perfil")
            }
            composable(AppRoute.Catalog.route) {
                Text("Pantalla Catalogo")
            }
            composable(AppRoute.LevelUp.route) {
                Text("Pantalla Level up")
            }
            composable(AppRoute.Register.route){
                RegisterScreen(navController = navController, viewModel = usuarioViewModel)

            }
        }

    }

}