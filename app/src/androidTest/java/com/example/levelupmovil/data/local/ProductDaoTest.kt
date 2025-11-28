package com.example.levelupmovil.data.local

import android.content.Context
import androidx.datastore.core.IOException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.levelupmovil.data.model.Category
import com.example.levelupmovil.data.model.Product
import com.example.levelupmovil.data.model.ProductCondition
import com.example.levelupmovil.repository.AppDataBase
import com.example.levelupmovil.repository.ProductDao
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductDaoTest {
    private lateinit var db: AppDataBase
    private lateinit var dao: ProductDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.productDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndGetProduct() = runBlocking {
        val product = Product(
            id = 1,
            name = "PC Gamer Ultra",
            brand = "Asus",
            imageUrl = "",
            price = 1000,
            category = Category.PC_GAMERS,
            condition = ProductCondition.NEW
        )

        dao.insertAll(listOf(product))

        val allProducts = dao.getAllProducts().first()

        assertEquals(1, allProducts.size)
        assertEquals("PC Gamer Ultra", allProducts[0].name)
    }

    @Test
    fun filterProductsByName_ShouldReturnMatches() = runBlocking {

        val p1 = Product(1, "Teclado Mecánico", "Razer", 10,Category.ACCESORIES,ProductCondition.NEW,"")
        val p2 = Product(2, "Mouse Gamer", "Logitech", 50,Category.MOUSES,  ProductCondition.NEW,"")
        val p3 = Product(3, "Teclado Membrana", "Genius",20, Category.ACCESORIES, ProductCondition.NEW,"")
        dao.insertAll(listOf(p1, p2, p3))

        val results = dao.getFilteredProducts("Teclado", null).first()

        assertEquals(2, results.size)
        assertTrue(results.any { it.name == "Teclado Mecánico" })
        assertTrue(results.any { it.name == "Teclado Membrana" })
    }

    @Test
    fun filterProductsByCategory_ShouldReturnExactMatches() = runBlocking {
        val p1 = Product(1, "PC", "X", 1, Category.PC_GAMERS, ProductCondition.NEW,"",)
        val p2 = Product(2, "Mouse", "Y",  1, Category.MOUSES, ProductCondition.NEW,"",)
        dao.insertAll(listOf(p1, p2))

        val results = dao.getFilteredProducts(null, Category.PC_GAMERS.dbValue).first()

        assertEquals(1, results.size)
        assertEquals("PC", results[0].name)
    }
}