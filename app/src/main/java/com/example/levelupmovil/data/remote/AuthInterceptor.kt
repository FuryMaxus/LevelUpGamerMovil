package com.example.levelupmovil.data.remote

import com.example.levelupmovil.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val userPreferences: UserPreferencesRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val noAuthHeader = request.header("No-Authentication")

        if (noAuthHeader == "true") {
            request = request.newBuilder()
                .removeHeader("No-Authentication")
                .build()
            return chain.proceed(request)
        }

        val token = userPreferences.getTokenInstant()

        val requestBuilder = chain.request().newBuilder()

        if (!token.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(requestBuilder.build())
    }
}