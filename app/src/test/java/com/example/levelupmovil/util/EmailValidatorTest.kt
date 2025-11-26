package com.example.levelupmovil.util

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class EmailValidatorTest {

    @Test
    fun `isValid should return true for valid emails`() {
        EmailValidator.isValid("jose@gmail.com") shouldBe true
        EmailValidator.isValid("nombre.apellido@empresa.cl") shouldBe true
    }

    @Test
    fun `isValid should return false for invalid emails`() {
        EmailValidator.isValid("jose-sin-arroba") shouldBe false
        EmailValidator.isValid("@sin-nombre.com") shouldBe false
        EmailValidator.isValid("") shouldBe false
    }
}