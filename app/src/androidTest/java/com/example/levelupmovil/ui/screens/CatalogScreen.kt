package com.example.levelupmovil.ui.screens

import androidx.activity.ComponentActivity
import androidx.compose.material3.Surface
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.example.levelupmovil.data.model.Category
import com.example.levelupmovil.data.model.Product
import com.example.levelupmovil.data.model.ProductCondition
import com.example.levelupmovil.ui.theme.LevelUpMovilTheme
import com.example.levelupmovil.viewmodel.CatalogViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class CatalogScreen {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val viewModel = mockk<CatalogViewModel>(relaxed = true)

    @Test
    fun catalog_shows_products_in_grid() {
        val fakeProducts = listOf(
            Product(1, "Monitor LG", "LG",  150000, Category.PC_GAMERS, ProductCondition.NEW,""),
            Product(2, "Teclado", "Razer",  50000, Category.ACCESORIES, ProductCondition.NEW,"")
        )

        every { viewModel.filteredProducts } returns MutableStateFlow(fakeProducts)
        every { viewModel.selectedCategory } returns MutableStateFlow(null)
        every { viewModel.isRefreshing } returns MutableStateFlow(false)

        composeTestRule.setContent {
            LevelUpMovilTheme {
                Surface {
                    CatalogScreen(
                        "",
                        viewModel,
                        {},
                        {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("product_grid").assertIsDisplayed()

        composeTestRule.onNodeWithText("Monitor LG").assertIsDisplayed()
        composeTestRule.onNodeWithText("Teclado").assertIsDisplayed()
    }
}