package com.example.levelupmovil.data.model

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class CategoryTest {

    @Test
    fun `fromDbValue should map exact string to Enum`() {
        Category.fromDbValue("Pc gamers") shouldBe Category.PC_GAMERS
        Category.fromDbValue("Mouses") shouldBe Category.MOUSES
    }

    @Test
    fun `fromDbValue should be case insensitive`() {
        Category.fromDbValue("pc GAMERS") shouldBe Category.PC_GAMERS
    }

    @Test
    fun `fromDbValue should return OTHERS for unknown values`() {
        Category.fromDbValue("CategoriaFantasma") shouldBe Category.OTHERS
    }

    @Test
    fun `fromDbValue should return OTHERS for null`() {
        Category.fromDbValue(null) shouldBe Category.OTHERS
    }
}