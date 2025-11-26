package com.example.levelupmovil.repository

import com.example.levelupmovil.data.remote.AuthApiService
import com.example.levelupmovil.data.remote.LoginResponse
import com.example.levelupmovil.data.remote.UserInfoResponse
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class AuthRepositoryTest {

    private val apiService: AuthApiService = mockk()
    private val userPreferences: UserPreferencesRepository = mockk(relaxed = true)
    private val repository = AuthRepository(apiService, userPreferences)

    @Test
    fun `login success should save token AND fetch user profile`() = runTest {

        val fakeToken = "jwt_token_123"
        val fakeUser = UserInfoResponse(1, "Juan", "juan@test.com", null)

        coEvery { apiService.login(any()) } returns LoginResponse(fakeToken)

        coEvery { apiService.getMyProfile() } returns fakeUser

        val result = repository.login("email", "pass")

        result.isSuccess shouldBe true

        coVerifyOrder {

            userPreferences.saveToken(fakeToken)

            apiService.getMyProfile()

            userPreferences.saveAuthData(fakeToken, fakeUser.name, fakeUser.email,fakeUser.address)
        }
    }

    @Test
    fun `login failure at first step should return failure`() = runTest {
        coEvery { apiService.login(any()) } throws Exception("Credenciales malas")

        val result = repository.login("email", "pass")

        result.isFailure shouldBe true
        coVerify(exactly = 0) { userPreferences.saveToken(any()) }
        coVerify(exactly = 0) { apiService.getMyProfile() }
    }

}