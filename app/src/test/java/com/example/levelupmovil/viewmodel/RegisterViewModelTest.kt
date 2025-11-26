package com.example.levelupmovil.viewmodel

import com.example.levelupmovil.repository.AuthRepository
import com.example.levelupmovil.rules.MainDispatcherExtension
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherExtension::class)
class RegisterViewModelTest {

    private val authRepository: AuthRepository = mockk(relaxed = true)
    private lateinit var viewModel: RegisterViewModel

    @Test
    fun `registrarUsuario success should invoke onSuccess callback`() = runTest {

        coEvery { authRepository.register(any(), any(), any(), any()) } returns Result.success(true)

        viewModel = RegisterViewModel(authRepository)

        viewModel.onNameChange("jose")
        viewModel.onEmailChange("jose@test.com")
        viewModel.onPasswordChange("123456")
        viewModel.onAddressChange("calle totalmente real")
        viewModel.onAceptaTerminosChange(true)

        var successCalled = false

        viewModel.registrarUsuario(onSuccess = { successCalled = true })

        successCalled shouldBe true
    }

    @Test
    fun `registrarUsuario failure should set errorMessage`() = runTest {

        val errorMsg = "El usuario ya existe"
        coEvery { authRepository.register(any(), any(), any(), any()) } returns Result.failure(Exception(errorMsg))

        viewModel = RegisterViewModel(authRepository)

        viewModel.onNameChange("jose")
        viewModel.onEmailChange("jose@test.com")
        viewModel.onPasswordChange("123456")
        viewModel.onAddressChange("calle totalmente real")
        viewModel.onAceptaTerminosChange(true)

        viewModel.registrarUsuario(onSuccess = {})

        viewModel.errorMessage.value shouldBe errorMsg
    }

    @Test
    fun `validarFormulario should fail with invalid email`() = runTest {
        viewModel = RegisterViewModel(authRepository)

        viewModel.onEmailChange("correo_sin_arroba")
        viewModel.onPasswordChange("123456")
        viewModel.onNameChange("jose")

        val esValido = viewModel.validarFormulario()

        esValido shouldBe false
        viewModel.estado.value.errors.email shouldBe "Error, El E-mail debe ser válido!"
    }
}