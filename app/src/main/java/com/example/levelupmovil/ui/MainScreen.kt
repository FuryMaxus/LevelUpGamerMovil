package com.example.levelupmovil.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
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
import com.example.levelupmovil.ui.screens.HomeScreen
import com.example.levelupmovil.ui.screens.LevelUpScreen
import com.example.levelupmovil.ui.screens.LoginScreen
import com.example.levelupmovil.ui.screens.ProfileScreen
import com.example.levelupmovil.ui.screens.RegisterScreen
import com.example.levelupmovil.viewmodel.AuthViewModel
import com.example.levelupmovil.viewmodel.CartViewModel
import com.example.levelupmovil.viewmodel.CatalogViewModel
import com.example.levelupmovil.viewmodel.LoginViewModel
import com.example.levelupmovil.viewmodel.ProfileViewModel
import com.example.levelupmovil.viewmodel.SearchViewModel
import com.example.levelupmovil.viewmodel.RegisterViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val context = LocalContext.current
    val navController = rememberNavController()

    val mainViewModel: MainViewModel = viewModel()
    val searchViewModel: SearchViewModel = viewModel()
    val cartViewModel: CartViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory)

    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()



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
                        inclusive = true,
                        popRoute = AppRoute.Catalog
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
                mainViewModel.navigateTo(
                    route,singleTop=true,
                    popRoute = AppRoute.Home,
                    inclusive = false
                )
            }
        }
    ) { innerPadding ->
        val animSpec = tween<IntOffset>(durationMillis = 300)
        val fadeSpec = tween<Float>(durationMillis = 300)
        NavHost(
            navController = navController,
            startDestination = AppRoute.Home.route,
            modifier = Modifier.padding(innerPadding),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = {it},animationSpec = animSpec) +
                        fadeIn(animationSpec = fadeSpec ) },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it }, animationSpec = animSpec) +
                        fadeOut(animationSpec = fadeSpec)
            },
        ) {
            composable(AppRoute.Home.route) {
                HomeScreen()
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
                val catalogViewModel: CatalogViewModel = viewModel(factory = CatalogViewModel.Factory)
                CatalogScreen(
                    catalogViewModel = catalogViewModel,
                    onProductClick = { product ->
                    },
                    searchQuery = query,
                    onAddToCartClick = { product ->
                        cartViewModel.addToCart(product)
                    }
                )
            }
            composable(AppRoute.LevelUp.route) {
                LevelUpScreen()
            }
            composable(AppRoute.Profile.route) {
                val profileViewModel: ProfileViewModel = viewModel(factory = ProfileViewModel.Factory)
                if(isLoggedIn) {
                    ProfileScreen(
                        viewModel = profileViewModel,
                        onLogout = {
                            authViewModel.onLogout()
                            mainViewModel.navigateTo(AppRoute.Login, inclusive = true)
                        }
                    )
                } else {
                    LaunchedEffect(Unit) {
                        mainViewModel.navigateTo(AppRoute.Login)
                    }
                }

            }
            composable(AppRoute.Cart.route){
                CartScreen(cartViewModel)
            }
            composable(AppRoute.Login.route) {
                val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory)
                LoginScreen(
                    viewModel = loginViewModel,
                    onLoginSuccess = {
                        authViewModel.isLoggedIn
                        mainViewModel.navigateTo(AppRoute.Profile, inclusive = true, popRoute = AppRoute.Login)
                    },
                    onRegisterClick = { mainViewModel.navigateTo(AppRoute.Register) }
                )
            }
            composable(AppRoute.Register.route){
                val registerViewModel: RegisterViewModel = viewModel(factory = RegisterViewModel.Factory)
                RegisterScreen(
                    viewModel = registerViewModel,
                    onRegisterSuccess = {
                        authViewModel.isLoggedIn
                        mainViewModel.navigateTo(AppRoute.Profile, inclusive = true)
                    },
                    onLoginClick = { mainViewModel.navigateBack() }
                )
            }
        }

    }

}