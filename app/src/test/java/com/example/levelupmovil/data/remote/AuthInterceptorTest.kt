package com.example.levelupmovil.data.remote

import com.example.levelupmovil.repository.UserPreferencesRepository
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.junit.jupiter.api.Test

class AuthInterceptorTest {

    private val userPreferences: UserPreferencesRepository = mockk()
    private val chain: Interceptor.Chain = mockk()
    private val interceptor = AuthInterceptor(userPreferences)

    @Test
    fun `intercept should ADD token header when token exists and call is NOT excluded`() {

        val token = "mi_super_token"
        every { userPreferences.getTokenInstant() } returns token

        val originalRequest = Request.Builder().url("http://api.com/me").build()
        every { chain.request() } returns originalRequest

        val requestSlot = slot<Request>()
        every { chain.proceed(capture(requestSlot)) } returns mockk<Response>()

        interceptor.intercept(chain)

        requestSlot.captured.header("Authorization") shouldBe "Bearer $token"
    }

    @Test
    fun `intercept should NOT add token when No-Authentication header is present`() {

        val token = "token_que_no_deberia_usarse"
        every { userPreferences.getTokenInstant() } returns token

        val loginRequest = Request.Builder()
            .url("http://api.com/login")
            .header("No-Authentication", "true")
            .build()

        every { chain.request() } returns loginRequest
        val requestSlot = slot<Request>()
        every { chain.proceed(capture(requestSlot)) } returns mockk<Response>()

        interceptor.intercept(chain)

        requestSlot.captured.header("Authorization") shouldBe null
        requestSlot.captured.header("No-Authentication") shouldBe null
    }

    @Test
    fun `intercept should NOT add token if token is null`() {

        every { userPreferences.getTokenInstant() } returns null

        val originalRequest = Request.Builder().url("http://api.com/productos").build()
        every { chain.request() } returns originalRequest
        val requestSlot = slot<Request>()
        every { chain.proceed(capture(requestSlot)) } returns mockk<Response>()


        interceptor.intercept(chain)


        requestSlot.captured.header("Authorization") shouldBe null
    }
}