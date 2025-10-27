package com.example.levelupmovil.viewmodel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {
    var searchQuery by mutableStateOf("")
        private set

    fun updateQuery(newQuery: String) {
        searchQuery = newQuery
    }
}