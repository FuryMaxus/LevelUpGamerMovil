package com.example.levelupmovil.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.levelupmovil.LevelUpMovilApplication
import com.example.levelupmovil.data.model.Product
import com.example.levelupmovil.repository.ProductRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditProductViewModel(
    private val repository: ProductRepository
): ViewModel() {

    var name by mutableStateOf("")
    var price by mutableStateOf("")
    var isLoading by mutableStateOf(false)

    private var originalProduct: Product? = null

    fun loadProduct(id: Int) {
        viewModelScope.launch {
            val product = repository.getProductById(id).first()
            originalProduct = product
            name = product.name
            price = product.price.toString()
        }
    }

    fun saveChanges(productId: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            try {
                val priceInt = price.toIntOrNull() ?: 0
                originalProduct?.let { current ->
                    repository.updateProduct(productId, name, priceInt, current)
                    onSuccess()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LevelUpMovilApplication)
                EditProductViewModel(app.container.productRepository)
            }
        }
    }
}