package com.example.levelupmovil.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.levelupmovil.data.model.CartItem

@Composable
fun CartItemRow(
    cartItem: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit
){
    val iconSize = 60.dp
    Column(
        modifier = Modifier.fillMaxWidth().padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = cartItem.product.name,
        )
        Text("${cartItem.product.price} x ${cartItem.quantity}")

        Row(
        ) {
            IconButton(onClick = onDecrease,modifier = Modifier.size(iconSize)) {
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "Reducir cantidad",
                )
            }
            IconButton(onClick = onIncrease,modifier = Modifier.size(iconSize)) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Incrementar cantidad",
                )
            }
            IconButton(onClick = onRemove,modifier = Modifier.size(iconSize)) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Quitar producto",
                )

            }
        }

        HorizontalDivider()

    }
}