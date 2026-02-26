package com.skymonkey.core.data.auth

import com.skymonkey.core.data.dto.request.RegisterRequest
import com.skymonkey.core.data.networking.post
import com.skymonkey.core.domain.auth.AuthService
import com.skymonkey.core.domain.util.DataError
import com.skymonkey.core.domain.util.EmptyResult
import io.ktor.client.HttpClient

class KtorAuthService(
    private val httpClient: HttpClient
): AuthService {
    override suspend fun register(
        email: String,
        username: String,
        password: String
    ): EmptyResult<DataError.Remote> {
        return httpClient.post(
            route = "auth/register",
            body = RegisterRequest(
                email = email,
                username = username,
                password = password
            )
        )
    }
}