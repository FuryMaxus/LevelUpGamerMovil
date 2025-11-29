package com.example.levelupmovil.repository

import com.example.levelupmovil.data.model.Category
import com.example.levelupmovil.data.model.Product
import com.example.levelupmovil.data.model.ProductCondition
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

    @Test
    fun `updateProduct should mix new data with old data and call API`() = runTest {

        val original =
            Product(1, "Viejo", "Marca", 10, Category.MOUSES, ProductCondition.NEW, "img_url")

        coEvery {
            api.updateProduct(any(), any())
        } returns ProductDto(1, "Nuevo", "Marca", "img", 20, "Mouses", "new")
        coEvery { dao.updateProductSimple(any(), any(), any()) } returns Unit

        repository.updateProduct(1, "Nuevo Nombre", 20, original)

        coVerify {
            api.updateProduct(
                id = 1,
                product = match { dto ->
                    dto.name == "Nuevo Nombre" &&
                            dto.price == 20 &&
                            dto.imageUrl == "img_url"
                }
            )
        }
    }
}
