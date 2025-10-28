package com.example.levelupmovil.navigation

sealed class AppRoute(val route: String) {
    data object Home: AppRoute("home")
    data object Catalog: AppRoute("catalog")
    data object Profile: AppRoute("profile")
    data object LevelUp: AppRoute("level-up")
    data object Register: AppRoute("register")
}