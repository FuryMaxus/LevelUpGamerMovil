package com.example.levelupmovil.viewmodel

import com.example.levelupmovil.data.model.Category
import com.example.levelupmovil.data.model.Product
import com.example.levelupmovil.data.model.ProductCondition
import com.example.levelupmovil.rules.MainDispatcherExtension
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherExtension::class)
class CartViewModelTest {

    private lateinit var viewModel: CartViewModel

    private val product1 =
        Product(1,"PC Gamer", "Asus", 1000,  Category.PC_GAMERS, ProductCondition.NEW,"")
    private val product2 =
        Product(2, "Mouse", "Logitech",  50, Category.MOUSES,ProductCondition.NEW, "")


    @BeforeEach
    fun setup() {
        viewModel = CartViewModel()
    }

    @Test
    fun `addToCart should add a new item to the list`() = runTest {

        viewModel.addToCart(product1)

        val items = viewModel.cartItems.value
        items shouldHaveSize 1
        items[0].product shouldBe product1
        items[0].quantity shouldBe 1
    }

    @Test
    fun `addToCart should increment quantity if item already exists`() = runTest {

        viewModel.addToCart(product1)
        viewModel.addToCart(product1)

        val items = viewModel.cartItems.value
        items shouldHaveSize 1
        items[0].quantity shouldBe 2
    }

    @Test
    fun `removeFromCart should remove the item completely`() = runTest {

        viewModel.addToCart(product1)
        viewModel.addToCart(product2)

        viewModel.removeFromCart(product1)

        val items = viewModel.cartItems.value
        items shouldHaveSize 1
        items[0].product shouldBe product2
    }

    @Test
    fun `updateQuantity should update quantity correctly`() = runTest {

        viewModel.addToCart(product1)

        viewModel.updateQuantity(product1, 5)

        viewModel.cartItems.value[0].quantity shouldBe 5
    }

    @Test
    fun `updateQuantity should remove item if new quantity is 0`() = runTest {

        viewModel.addToCart(product1)

        viewModel.updateQuantity(product1, 0)

        viewModel.cartItems.value.shouldBeEmpty()
    }

    @Test
    fun `clearCart should remove all items`() = runTest {

        viewModel.addToCart(product1)
        viewModel.addToCart(product2)

        viewModel.clearCart()

        viewModel.cartItems.value.shouldBeEmpty()
    }
}