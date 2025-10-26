package com.example.levelupmovil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.levelupmovil.model.Category
import com.example.levelupmovil.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.levelupmovil.repository.ProductDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CatalogViewModel(private val productDao: ProductDao): ViewModel() {

    private val _filteredProducts = MutableStateFlow<List<Product>>(emptyList())
    val filteredProducts: StateFlow<List<Product>> get() = _filteredProducts

    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory: StateFlow<Category?> = _selectedCategory

    private var currentQuery = ""

    init {
        filterProducts()
    }

    fun filterProducts(query: String = currentQuery, category: Category? = _selectedCategory.value) {
        currentQuery = query
        _selectedCategory.value = category

        viewModelScope.launch(Dispatchers.IO) {
            val results = productDao.getFilteredProducts(
                query.takeIf { it.isNotEmpty() },
                category?.name
            )
            _filteredProducts.value = results
        }
    }

    fun selectCategory(category: Category?) {
        _selectedCategory.value = category
        filterProducts(currentQuery, category)
    }
}
