package com.example.levelupmovil.viewmodel

import com.example.levelupmovil.data.model.UserData
import com.example.levelupmovil.repository.UserPreferencesRepository
import com.example.levelupmovil.rules.MainDispatcherExtension
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import io.kotest.matchers.shouldBe

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainDispatcherExtension::class)
class ProfileViewModelTest {

    private val userPreferencesRepository: UserPreferencesRepository = mockk(relaxed = true)
    private lateinit var viewModel: ProfileViewModel

    @Test
    fun `userData should expose data from repository`() = runTest {

        val fakeUser = UserData(
            name = "Usuario Test",
            email = "test@correo.com",
            password = "",
            profilePicUri = "file://some/path.jpg",
            address = "Calle Falsa 123",
            role = "ROL_CLIENTE",
        )

        every { userPreferencesRepository.userData } returns flowOf(fakeUser)

        viewModel = ProfileViewModel(userPreferencesRepository)

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.userData.collect()
        }

        viewModel.userData.value shouldBe fakeUser
    }
}