package com.example.levelupmovil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupmovil.model.UserData
import com.example.levelupmovil.repository.UserDataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn


class ProfileViewModel(
    private val userDataStore: UserDataStore
): ViewModel() {
    val userData: StateFlow<UserData> = userDataStore.userDataFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UserData(name = "", email = "", password = "")
    )
}