package com.example.levelupmovil.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupmovil.model.Category
import com.example.levelupmovil.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CatalogViewModel: ViewModel() {

    private val _allProducts = listOf(
        Product(1, "Producto 1","brand1", 10.0, Category.PC_GAMERS,"https://www.clinicaswecan.com/imagenes/blogs/9_imagen_5bce843dd76db8c939d5323dd3e54ec9.jpg"),
        Product(2, "Producto 2","brand2", 20.0, Category.CONSOLES, "https://upload.wikimedia.org/wikipedia/commons/2/20/Avant-Tower-Gaming-PC.png"),
        Product(3, "Producto 3","brand3",30.0,Category.ACCESORIES, "https://upload.wikimedia.org/wikipedia/commons/2/20/Avant-Tower-Gaming-PC.png")
    )

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
