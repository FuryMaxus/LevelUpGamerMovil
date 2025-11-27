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
import com.example.levelupmovil.viewmodel.CartViewModel
import org.junit.Rule
import org.junit.Test

class CartScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private val viewModel = CartViewModel()

    @Test
    fun cart_shows_items_and_total() {

        val producto = Product(
            1,
            "Mouse Gamer",
            "Logitech",
            50000,
            Category.MOUSES,
            ProductCondition.NEW,
            "",
        )

        viewModel.addToCart(producto)
        viewModel.addToCart(producto)

        composeTestRule.setContent {
            LevelUpMovilTheme {
                Surface {
                    CartScreen(cartViewModel = viewModel)
                }
            }
        }

        composeTestRule.onNodeWithTag("cart_list").assertIsDisplayed()
        composeTestRule.onNodeWithText("Mouse Gamer").assertIsDisplayed()
    }
}