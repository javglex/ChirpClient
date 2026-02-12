package com.skymonkey.core.data.networking

import com.skymonkey.core.domain.util.DataError
import com.skymonkey.core.domain.util.Result
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import java.net.ConnectException
import java.net.UnknownHostException

actual suspend fun <T> platformSafeCall(
    execute: suspend () -> HttpResponse,
    handleResponse: suspend (HttpResponse) -> Result<T, DataError.Remote>
): Result<T, DataError.Remote> {
    /*
     * The following network exception are only available on Java/Android.
     * Which is why we need to catch them in this platform specific module.
     * They come from the ktor underlying okhttp library.
     */
    return try {
        val response = execute()
        handleResponse(response)
    } catch(e: UnknownHostException) {
        Result.Failure(DataError.Remote.NO_INTERNET_CONNECTION)
    } catch(e: UnresolvedAddressException) {
        Result.Failure(DataError.Remote.NO_INTERNET_CONNECTION)
    } catch(e: ConnectException) {
        Result.Failure(DataError.Remote.NO_INTERNET_CONNECTION)
    } catch(e: SocketTimeoutException) {
        Result.Failure(DataError.Remote.REQUEST_TIMEOUT)
    } catch(e: HttpRequestTimeoutException) {
        Result.Failure(DataError.Remote.REQUEST_TIMEOUT)
    } catch(e: SerializationException) {
        Result.Failure(DataError.Remote.SERIALIZATION_ERROR)
    } catch (e: Exception) {
        // rethrow coroutine cancellation exception, else parent coroutine scope
        // will not be cancelled and some weird things can happen
        currentCoroutineContext().ensureActive()
        Result.Failure(DataError.Remote.UNKNOWN)
    }
}