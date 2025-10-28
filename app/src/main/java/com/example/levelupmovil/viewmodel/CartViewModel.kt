package com.example.levelupmovil.viewmodel

import androidx.lifecycle.ViewModel
import com.example.levelupmovil.model.CartItem
import com.example.levelupmovil.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.collections.remove

class CartViewModel: ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> get() =_cartItems

    fun addToCart(product: Product) {
        val updatedList = _cartItems.value.toMutableList()
        val index = updatedList.indexOfFirst { it.product.id == product.id }

        if (index >= 0) {
            val existing = updatedList[index]
            updatedList[index] = existing.copy(quantity = existing.quantity + 1)
        } else {
            updatedList.add(CartItem(product = product))
        }

        _cartItems.value = updatedList.toList()
    }

    fun removeFromCart(product: Product){
        _cartItems.value = _cartItems.value.filterNot { it.product.id == product.id }
    }

    fun updateQuantity(product: Product,newQuantity: Int) {
        val updatedList = _cartItems.value.toMutableList()
        val index = updatedList.indexOfFirst { it.product.id == product.id }

        if (index >= 0) {
            val existing = updatedList[index]
            if (newQuantity > 0) {
                updatedList[index] = existing.copy(quantity = newQuantity)
            } else {
                updatedList.removeAt(index)
            }
            _cartItems.value = updatedList.toList()
        }

    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }

    fun getTotalPrice(): Int {
        return _cartItems.value.sumOf { it.product.price * it.quantity }
    }


}