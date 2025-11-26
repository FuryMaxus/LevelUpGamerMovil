package com.example.levelupmovil.viewmodel

import com.example.levelupmovil.repository.UserPreferencesRepository
import com.example.levelupmovil.rules.MainDispatcherExtension
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import io.kotest.matchers.shouldBe
import io.mockk.coVerify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@ExtendWith(MainDispatcherExtension::class)
class AuthViewModelTest {

    private val userPreferencesRepository: UserPreferencesRepository = mockk(relaxed = true)
    private lateinit var viewModel: AuthViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `isLoggedIn should be TRUE when repository returns a token`() = runTest {

        every { userPreferencesRepository.authToken } returns flowOf("token_jwt_valido_123")

        viewModel = AuthViewModel(userPreferencesRepository)

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.isLoggedIn.collect()
        }
        viewModel.isLoggedIn.value shouldBe true
    }

    @Test
    fun `isLoggedIn should be FALSE when repository returns null`() = runTest {

        every { userPreferencesRepository.authToken } returns flowOf(null)

        viewModel = AuthViewModel(userPreferencesRepository)

        viewModel.isLoggedIn.value shouldBe false
    }

    @Test
    fun `onLogout should call clearAuthData in repository`() = runTest {

        every { userPreferencesRepository.authToken } returns flowOf("token")
        viewModel = AuthViewModel(userPreferencesRepository)

        viewModel.onLogout()

        coVerify(exactly = 1) { userPreferencesRepository.clearAuthData() }
    }
}
