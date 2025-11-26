package com.example.levelupmovil.repository

import com.example.levelupmovil.data.model.ProductDto
import com.example.levelupmovil.data.remote.ProductApiService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class ProductRepositoryTest {
    private val api: ProductApiService = mockk()
    private val dao: ProductDao = mockk(relaxed = true)
    private val repository = ProductRepository(api, dao)

    @Test
    fun `refreshProducts should fetch from API and save to DAO`() = runTest {

        val fakeDto = ProductDto(1, "PC", "Asus", "url", 100, "Pc gamers", "new")
        coEvery { api.getAllProducts() } returns listOf(fakeDto)

        repository.refreshProducts()

        coVerify { dao.insertAll(any()) }
    }

    @Test
    fun `refreshProducts should handle API errors gracefully`() = runTest {
        coEvery { api.getAllProducts() } throws RuntimeException("Sin internet")

        repository.refreshProducts()

        coVerify(exactly = 0) { dao.insertAll(any()) }
    }
}
