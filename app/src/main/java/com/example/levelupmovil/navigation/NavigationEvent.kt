package com.example.levelupmovil.navigation

sealed class NavigationEvent{
    data class NavigateTo(
        val appRoute: AppRoute,
        val popRoute: AppRoute ?= null,
        val inclusive: Boolean = false,
        val singleTop: Boolean = false
    ): NavigationEvent()
    object PopBackStack: NavigationEvent()
    object NavigateUp: NavigationEvent()
}