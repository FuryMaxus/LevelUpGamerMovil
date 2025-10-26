package com.example.levelupmovil.viewmodel

import androidx.lifecycle.ViewModel

import com.example.levelupmovil.model.Category
import com.example.levelupmovil.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.levelupmovil.model.sampleProducts

class CatalogViewModel: ViewModel() {

    private val _allProducts = sampleProducts

    private val _filteredProducts = MutableStateFlow<List<Product>>(_allProducts)
    val filteredProducts: StateFlow<List<Product>> get() = _filteredProducts

    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory: StateFlow<Category?> = _selectedCategory
    private var currentQuery = ""

    fun filterProducts(query: String = currentQuery, category: Category? = _selectedCategory.value) {
        currentQuery = query
        _selectedCategory.value = category

        val filtered = _allProducts.filter { product ->
            val matchesQuery = product.name.contains(query, ignoreCase = true)
            val matchesCategory = category == null || product.category == category
            matchesQuery && matchesCategory
        }

        println("Query: '$query', Category: '$category', Filtrados: ${filtered.map { it.name }}")
        _filteredProducts.value = filtered
    }

    fun selectCategory(category: Category?) {
        _selectedCategory.value = category
        filterProducts(currentQuery, category)
    }
}
