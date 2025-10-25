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
import com.example.levelupmovil.model.Product
import com.example.levelupmovil.navigation.AppRoute
import com.example.levelupmovil.navigation.NavigationEvent
import com.example.levelupmovil.ui.components.BottomBar
import com.example.levelupmovil.ui.components.TopBar
import com.example.levelupmovil.ui.screens.CatalogScreen
import com.example.levelupmovil.viewmodel.MainViewModel
import com.example.levelupmovil.viewmodel.SearchViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val mainViewModel: MainViewModel = viewModel()
    val navController = rememberNavController()
    val searchViewModel: SearchViewModel = viewModel()

    val sampleProducts = listOf(
        Product(1, "Producto 1", 10.0, "https://www.clinicaswecan.com/imagenes/blogs/9_imagen_5bce843dd76db8c939d5323dd3e54ec9.jpg"),
        Product(2, "Producto 2", 20.0, "https://upload.wikimedia.org/wikipedia/commons/2/20/Avant-Tower-Gaming-PC.png"),
        Product(3, "Producto 3", 30.0, "https://upload.wikimedia.org/wikipedia/commons/2/20/Avant-Tower-Gaming-PC.png")
    )


    LaunchedEffect(Unit) {
        mainViewModel.navEvents.collect{ event ->
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
        topBar = {
            TopBar(
                viewModel = searchViewModel,
                onCartClick = {
                    mainViewModel.navigateTo(AppRoute.LevelUp)
                }
            )
        },
        bottomBar = {
            BottomBar{route ->
                mainViewModel.navigateTo(route,singleTop=true,popRoute = AppRoute.Home)
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
                CatalogScreen(
                    products = sampleProducts,
                    onProductClick = { product ->

                    }
                )
            }
            composable(AppRoute.LevelUp.route) {
                Text("Pantalla Level up")
            }
        }

    }

}