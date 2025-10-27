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
        val currentList = _cartItems.value.toMutableList()
        val existingItem = currentList.find {it.product.id == product.id}

        if (existingItem != null) {
            existingItem.quantity++
        }else{
            currentList.add(CartItem(product))
        }
        _cartItems.value = currentList
    }

    fun removeFromCart(product: Product){
        val currentList = _cartItems.value.toMutableList()
        currentList.removeAll { it.product.id == product.id }
        _cartItems.value = currentList
    }

    fun updateQuantity(product: Product,newQuantity: Int) {
        val currentList = _cartItems.value.toMutableList()
        val item = currentList.find { it.product.id == product.id }
        if (item != null) {
            if (newQuantity > 0){
                item.quantity = newQuantity
            }else {
                currentList.remove(item)
            }
        }
        _cartItems.value = currentList

    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }

    fun getTotalPrice(): Int {
        return _cartItems.value.sumOf { it.product.price * it.quantity }
    }


}