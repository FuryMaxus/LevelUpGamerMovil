package com.example.levelupmovil.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.levelupmovil.viewmodel.SearchViewModel
import com.example.levelupmovil.R
import androidx.compose.ui.Alignment
import com.example.levelupmovil.viewmodel.CartViewModel
import com.example.levelupmovil.viewmodel.CatalogViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    searchViewModel: SearchViewModel = viewModel(),
    onCartClick: () -> Unit,
    onSearch: (String) -> Unit,
    cartViewModel: CartViewModel,
    onLogoClick: () -> Unit
){

    val cartItems by cartViewModel.cartItems.collectAsState()
    val cartCount = cartItems.sumOf { it.quantity }

    TopAppBar(
         colors = TopAppBarDefaults.topAppBarColors(
             containerColor = MaterialTheme.colorScheme.primaryContainer,
             titleContentColor = MaterialTheme.colorScheme.primary
         ),
        navigationIcon = {
            IconButton(onClick = onLogoClick) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "App logo",
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,

                    )
            }
        },
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){



                Spacer(modifier = Modifier.width(8.dp))

                SearchBar(
                    query = searchViewModel.searchQuery,
                    onQueryChange = { searchViewModel.updateQuery(it) },
                    onSearch = { onSearch(searchViewModel.searchQuery) },
                    modifier = Modifier
                        .weight(1f)
                        .height(54.dp)
                )
            }
        },
        actions = {
            Box{
                IconButton(onClick = onCartClick) {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = "Carrito"
                    )
                }
                if(cartCount > 0) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 6.dp, end = 6.dp)
                            .size(18.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.error),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = cartCount.toString(),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White
                        )
                    }
                }
            }
        }
    )
}