package com.example.levelupmovil.ui.screens

import android.R
import android.content.ClipData
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.levelupmovil.ui.components.CartItemRow
import com.example.levelupmovil.viewmodel.CartViewModel

@Composable
fun CartScreen(cartViewModel: CartViewModel){
    val cartItems by cartViewModel.cartItems.collectAsState()
    val total = cartItems.sumOf { it.product.price * it.quantity }
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Carrito de Compras", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ){
            item{
                HorizontalDivider()
            }
            items(cartItems) { item ->
                CartItemRow(
                    cartItem = item,
                    onIncrease = { cartViewModel.updateQuantity(item.product, item.quantity + 1) },
                    onDecrease = { cartViewModel.updateQuantity(item.product, item.quantity - 1) },
                    onRemove = { cartViewModel.removeFromCart(item.product) }
                )
            }
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Total: $${total}",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Continuar Compra")
                }
                Button(
                    onClick = {cartViewModel.clearCart()},
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Vaciar carrito")
                }
            }
        }
    }
}