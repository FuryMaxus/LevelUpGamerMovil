package com.example.levelupmovil.navigation

sealed class AppRoute(val route: String) {
    data object Home: AppRoute("home")
    data object Catalog: AppRoute("catalog")
}