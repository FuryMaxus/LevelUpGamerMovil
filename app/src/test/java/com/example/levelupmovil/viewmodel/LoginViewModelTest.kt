package com.example.levelupmovil.viewmodel

import com.example.levelupmovil.repository.AuthRepository
import com.example.levelupmovil.rules.MainDispatcherExtension
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import io.kotest.matchers.string.shouldContain

@ExtendWith(MainDispatcherExtension::class)
class LoginViewModelTest {

    private val authRepository: AuthRepository = mockk(relaxed = true)

    private lateinit var viewModel: LoginViewModel

    @Test
    fun `tryLogin success should invoke onSuccess callback`() = runTest {

        coEvery { authRepository.login(any(), any()) } returns Result.success(true)

        viewModel = LoginViewModel(authRepository)

        viewModel.onEmailChange("test@test.com")
        viewModel.onPasswordChange("123456")

        var successCalled = false

        viewModel.tryLogin(onSuccess = { successCalled = true })

        successCalled shouldBe true
    }

    @Test
    fun `tryLogin failure should update state with error message`() = runTest {

        val errorMessage = "Credenciales incorrectas"
        coEvery { authRepository.login(any(), any()) } returns Result.failure(Exception(errorMessage))

        viewModel = LoginViewModel(authRepository)
        viewModel.onEmailChange("test@test.com")
        viewModel.onPasswordChange("123456")

        viewModel.tryLogin(onSuccess = {})

        viewModel.loginData.value.errors.email shouldContain errorMessage
    }
}
