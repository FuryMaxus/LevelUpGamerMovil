package com.example.levelupmovil.repository

import com.example.levelupmovil.data.model.Category
import com.example.levelupmovil.data.model.ProductCondition
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class ConvertersTest {
    private val converters = Converters()

    @Test
    fun `fromCategory should return correct string`() {
        converters.fromCategory(Category.PC_GAMERS) shouldBe "Pc gamers"
    }

    @Test
    fun `toCategory should return correct Enum`() {
        converters.toCategory("Pc gamers") shouldBe Category.PC_GAMERS
        converters.toCategory("Inexistente") shouldBe Category.OTHERS
    }

    @Test
    fun `fromCondition should return correct string`() {
        converters.fromCondition(ProductCondition.SECOND_HAND) shouldBe "second"
    }

    @Test
    fun `toCondition should return correct Enum`() {
        converters.toCondition("second") shouldBe ProductCondition.SECOND_HAND
        converters.toCondition("new") shouldBe ProductCondition.NEW
    }

}