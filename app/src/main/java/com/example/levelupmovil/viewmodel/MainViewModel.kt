package com.example.levelupmovil.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupmovil.navigation.AppRoute
import com.example.levelupmovil.navigation.NavigationEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _navEvents = MutableSharedFlow<NavigationEvent>()
    val navEvents = _navEvents.asSharedFlow()

    fun navigateTo(
        route: AppRoute,
        popRoute: AppRoute? = null,
        inclusive: Boolean = false,
        singleTop: Boolean = true // <- ahora sí puedes pasarlo
    ){
        viewModelScope.launch {
            _navEvents.emit(
                NavigationEvent.NavigateTo(
                    appRoute = route,
                    popRoute = popRoute,
                    inclusive = inclusive,
                    singleTop = singleTop
                )
            )
        }
    }

    fun navigateBack(){
        viewModelScope.launch {
            _navEvents.emit(NavigationEvent.PopBackStack)
        }
    }

    fun navigateUp(){
        viewModelScope.launch {
            _navEvents.emit(NavigationEvent.NavigateUp)
        }
    }
}