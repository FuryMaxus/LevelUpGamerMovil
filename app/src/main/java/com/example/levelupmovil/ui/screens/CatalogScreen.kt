package com.example.levelupmovil.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.levelupmovil.data.model.Product
import com.example.levelupmovil.ui.components.CategorySlider
import com.example.levelupmovil.ui.components.ProductItem
import com.example.levelupmovil.viewmodel.CatalogViewModel

@Composable
fun CatalogScreen(
    searchQuery: String,
    catalogViewModel: CatalogViewModel,
    onProductClick: (Product) -> Unit,
    onAddToCartClick: (Product) -> Unit
) {

    val products by catalogViewModel.filteredProducts.collectAsStateWithLifecycle()
    val selectedCategory by catalogViewModel.selectedCategory.collectAsStateWithLifecycle()
    val isRefreshing by catalogViewModel.isRefreshing.collectAsStateWithLifecycle()

    LaunchedEffect(searchQuery) {
        catalogViewModel.updateSearchQuery(searchQuery)
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { catalogViewModel.onPullToRefresh() },
        modifier = Modifier.fillMaxSize()
    ){
        Column {
            CategorySlider(
                selectedCategory = selectedCategory,
                onCategoryClick = { category ->
                    catalogViewModel.selectCategory(category)
                }
            )
            LazyVerticalGrid(
                contentPadding = PaddingValues(8.dp),
                columns = GridCells.Adaptive(minSize = 116.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.testTag("product_grid")
            ) {
                items(products) {p ->
                    ProductItem(
                        product = p,
                        onClick = { onProductClick(p) },
                        onButtonClick = {onAddToCartClick(p)}
                    )
                }
            }
        }
    }



}