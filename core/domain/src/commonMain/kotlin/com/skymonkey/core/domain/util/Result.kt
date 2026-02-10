package com.skymonkey.core.domain.util

/**
 * D is data type we want to return
 * E is error type we want to return
 */
sealed interface Result<out D, out E: Error> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Failure<out E: Error>(val error: E) : Result<Nothing, E>
}

inline fun <T, E: Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when (this) {
        is Result.Success -> Result.Success(map(data))
        is Result.Failure -> Result.Failure(error)
    }
}

inline fun <T, E: Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Success -> {
            action(data)
            this
        }
        is Result.Failure -> this
    }
}

inline fun <T, E: Error> Result<T, E>.onFailure(action: (E) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Failure -> {
            action(error)
            this
        }
        is Result.Success -> this
    }
}

/*
 * Maps our success case to an empty result.
 */
fun <T, E: Error> Result<T, E>.asEmptyResult(): EmptyResult<E> {
    return map { Unit }
}

typealias EmptyResult<E> = Result<Unit, E>

/**
 * Example of how to use the .map, .onSuccess and .onFailure extension functions
 */
private fun loadChats(): Result<List<String>, DataError.Remote> {
    return Result.Success(listOf("Chat 1", "Chat 2"))
}

private fun fetchChats() {
    val result = loadChats()
    result
        .map { chats ->
            chats.map { chat ->
                chat.uppercase()
            }
        }
        .onSuccess { chats ->
            println(chats)
        }
        .onFailure { error ->
            println(error)
        }
}