package com.example.levelupmovil.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.levelupmovil.navigation.AppRoute
import com.example.levelupmovil.navigation.NavigationEvent
import com.example.levelupmovil.repository.AppDataBase
import com.example.levelupmovil.ui.components.BottomBar
import com.example.levelupmovil.viewmodel.MainViewModel
import com.example.levelupmovil.ui.components.TopBar
import com.example.levelupmovil.ui.screens.CartScreen
import com.example.levelupmovil.ui.screens.CatalogScreen
import com.example.levelupmovil.ui.screens.ProfileScreen
import com.example.levelupmovil.viewmodel.CartViewModel
import com.example.levelupmovil.viewmodel.CatalogViewModel
import com.example.levelupmovil.viewmodel.CatalogViewModelFactory
import com.example.levelupmovil.viewmodel.SearchViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val context = LocalContext.current
    val navController = rememberNavController()

    val mainViewModel: MainViewModel = viewModel()
    val searchViewModel: SearchViewModel = viewModel()
    val cartViewModel: CartViewModel = viewModel()



    val usuarioViewModel: UsuarioViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()

    LaunchedEffect(Unit) {
        mainViewModel.navEvents.collect{ event ->
            when(event) {
                is NavigationEvent.NavigateTo -> {
                    val routeWithArgs = event.args?.entries?.joinToString("&") { "${it.key}=${it.value}" }
                        ?.let { "${event.appRoute.route}?$it" } ?: event.appRoute.route

                    navController.navigate(routeWithArgs) {
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
                searchViewModel = searchViewModel,
                onCartClick = {
                    mainViewModel.navigateTo(
                        AppRoute.Cart,
                        singleTop = true,
                    )
                },
                onSearch = {
                        query ->
                    mainViewModel.navigateTo(
                        AppRoute.Catalog,
                        args = mapOf("searchQuery" to query.trim())
                    )
                },
                cartViewModel = cartViewModel,
                onLogoClick = {
                    mainViewModel.navigateTo(
                        AppRoute.Home,
                        singleTop = true,
                    )
                }
            )
        },
        bottomBar = {
            BottomBar{route ->
                mainViewModel.navigateTo(route,singleTop=true, popRoute = AppRoute.Home, inclusive = false)
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
            composable(
                route = AppRoute.Catalog.route + "?searchQuery={searchQuery}",
                arguments = listOf(
                    navArgument("searchQuery"){
                        type = NavType.StringType
                        defaultValue = ""
                    }
                )
            ) { backStackEntry ->

                val query = backStackEntry.arguments?.getString("searchQuery") ?: ""
                val productDao = AppDataBase.getDatabase(context).productDao()
                val catalogViewModel: CatalogViewModel = viewModel(
                    factory = CatalogViewModelFactory(productDao)
                )

                CatalogScreen(
                    onProductClick = { product ->
                    },
                    searchQuery = query,
                    catalogViewModel = catalogViewModel,
                    onAddToCartClick = { product ->
                        cartViewModel.addToCart(product)
                    }
                )
            }
            composable(AppRoute.LevelUp.route) {
                Text("Pantalla Level up")
            }
            composable(AppRoute.Profile.route) {
                ProfileScreen(
                    onLoginSucces = {
                        mainViewModel.navigateTo(
                            AppRoute.Home,
                            singleTop = true,
                            popRoute = AppRoute.Profile,
                            inclusive = true
                        )
                    },
                    onRegisterSucces = {
                        mainViewModel.navigateTo(AppRoute.Profile)
                    }
                )
            }
            composable(AppRoute.Cart.route){
                CartScreen(cartViewModel)
            }
        }

    }

}