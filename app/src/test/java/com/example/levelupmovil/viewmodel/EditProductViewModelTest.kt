package com.example.levelupmovil.viewmodel

import com.example.levelupmovil.data.model.Category
import com.example.levelupmovil.data.model.Product
import com.example.levelupmovil.data.model.ProductCondition
import com.example.levelupmovil.repository.ProductRepository
import com.example.levelupmovil.rules.MainDispatcherExtension
import io.kotest.matchers.shouldBe
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherExtension::class)
class EditProductViewModelTest {
    private val repository: ProductRepository = mockk(relaxed = true)
    private lateinit var viewModel: EditProductViewModel

    @Test
    fun `loadProduct should populate fields from repository`() = runTest {

        val product = Product(1, "PC", "Asus",  1000, Category.PC_GAMERS, ProductCondition.NEW,"")
        every { repository.getProductById(1) } returns flowOf(product)

        viewModel = EditProductViewModel(repository)

        viewModel.loadProduct(1)

        viewModel.name shouldBe "PC"
        viewModel.price shouldBe "1000"
    }

    @Test
    fun `saveChanges should call updateProduct with correct data`() = runTest {

        val original = Product(1, "PC", "Asus",  1000, Category.PC_GAMERS, ProductCondition.NEW,"img",)
        every { repository.getProductById(1) } returns flowOf(original)
        viewModel = EditProductViewModel(repository)
        viewModel.loadProduct(1)

        viewModel.name = "PC Gamer Pro"
        viewModel.price = "2000"
        viewModel.saveChanges(1, onSuccess = {})


        coVerify {
            repository.updateProduct(
                id = 1,
                "PC Gamer Pro",
                2000,
                original
            )
        }
    }
}