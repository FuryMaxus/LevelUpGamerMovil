package com.example.levelupmovil.ui.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ManageSearch
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.levelupmovil.navigation.AppRoute

@Composable
fun BottomBar(onNavigate: (AppRoute) -> Unit) {

    val items = listOf(
        BottomNavItem(AppRoute.Home, Icons.Default.Home,"Inicio"),
        BottomNavItem(AppRoute.Profile, Icons.Default.Person,"Mi cuenta"),
        BottomNavItem(AppRoute.Catalog, Icons.AutoMirrored.Filled.ManageSearch,"Catalogo"),
        BottomNavItem(AppRoute.LevelUp, Icons.Default.Star,"Level-up"),
        BottomNavItem(AppRoute.Register, Icons.Default.PersonAdd, "Registro")
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = false,
                onClick = {
                    onNavigate(item.route)

                }
            )
        }
    }
}

data class BottomNavItem(
    val route: AppRoute,
    val icon: ImageVector,
    val label: String
)