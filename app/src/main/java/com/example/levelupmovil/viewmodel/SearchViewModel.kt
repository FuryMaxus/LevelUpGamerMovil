package com.example.levelupmovil.viewmodel
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {

    var query = mutableStateOf("")
        private set

    fun onQueryChange(newValue: String) {
        query.value = newValue
    }
}