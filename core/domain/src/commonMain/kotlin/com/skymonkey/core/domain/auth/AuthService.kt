package com.skymonkey.core.domain.auth

import com.skymonkey.core.domain.util.DataError
import com.skymonkey.core.domain.util.EmptyResult

interface AuthService {
    suspend fun register(
        email: String,
        username: String,
        password: String
    ): EmptyResult<DataError.Remote>


}