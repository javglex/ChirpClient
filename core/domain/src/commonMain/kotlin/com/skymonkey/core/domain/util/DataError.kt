package com.skymonkey.core.domain.util

/**
 * These types of errors are for data retrieval related errors.
 * Such as network errors (remote), database errors (local), etc.
 */
sealed interface DataError: Error {
    enum class Remote: DataError{
        BAD_REQUEST,
        REQUEST_TIMEOUT,
        UNAUTHORIZED,
        FORBIDDEN,
        NOT_FOUND,
        CONFLICT,
        TOO_MANY_REQUESTS,
        NO_INTERNET_CONNECTION,
        PAYLOAD_TOO_LARGE,
        INTERNAL_SERVER_ERROR,
        SERVICE_UNAVAILABLE,
        SERIALIZATION_ERROR,
        UNKNOWN
    }
    enum class Local: DataError {
        DISK_FULL,
        FILE_NOT_FOUND,
        DATABASE_ERROR,
        CACHE_ERROR,
        UNKNOWN
    }
}