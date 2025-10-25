package com.example.levelupmovil.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.levelupmovil.model.Product
import com.example.levelupmovil.ui.components.ProductItem
import com.example.levelupmovil.viewmodel.CatalogViewModel

@Composable
fun CatalogScreen(
    searchQuery: String,
    catalogViewModel: CatalogViewModel,
    onProductClick: (Product) -> Unit
) {

    LaunchedEffect(searchQuery) {
        catalogViewModel.filterProducts(searchQuery)
    }

    val products by catalogViewModel.filteredProducts.collectAsState()

    LazyVerticalGrid(
        contentPadding = PaddingValues(8.dp),
        columns = GridCells.Adaptive(minSize = 116.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(products) {p ->
            ProductItem(p, onClick = { onProductClick(p) })
        }
    }
}