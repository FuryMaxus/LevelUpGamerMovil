package com.example.levelupmovil.viewmodel

import com.example.levelupmovil.data.model.Category
import com.example.levelupmovil.data.model.Product
import com.example.levelupmovil.data.model.ProductCondition
import com.example.levelupmovil.repository.ProductRepository
import com.example.levelupmovil.rules.MainDispatcherExtension
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import io.kotest.matchers.shouldBe
import io.mockk.verify

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainDispatcherExtension::class)
class CatalogViewModelTest {

    private val repository: ProductRepository = mockk(relaxed = true)
    private lateinit var viewModel: CatalogViewModel

    @Test
    fun `filteredProducts should update when searchQuery changes`() = runTest {

        val listaProductos = listOf(
            Product(1, "PC Gamer", "Asus", 1000, Category.PC_GAMERS,ProductCondition.NEW, "" )
        )

        every { repository.getFilteredProducts(any(), any()) } returns flowOf(listaProductos)

        viewModel = CatalogViewModel(repository)

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.filteredProducts.collect()
        }

        viewModel.updateSearchQuery("Asus")

        viewModel.filteredProducts.value shouldBe listaProductos

        verify { repository.getFilteredProducts("Asus", null) }
    }
}