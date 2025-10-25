package com.example.levelupmovil.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.levelupmovil.viewmodel.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    viewModel: SearchViewModel = viewModel(),
    onCartClick: () -> Unit = {}
){
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    TopAppBar(
         colors = TopAppBarDefaults.topAppBarColors(
             containerColor = MaterialTheme.colorScheme.primaryContainer,
             titleContentColor = MaterialTheme.colorScheme.primary
         ),
        title = {
            SearchBar(
                query = viewModel.query.value,
                onQueryChange = viewModel::onQueryChange
            )
        },
        actions = {
            IconButton(onClick = onCartClick) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
            }
        }
     )


}