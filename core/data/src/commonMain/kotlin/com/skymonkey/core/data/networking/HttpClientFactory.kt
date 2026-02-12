package com.skymonkey.core.data.networking

import com.skymonkey.core.domain.logging.ChirpLogger
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class HttpClientFactory(
    private val chirpLogger: ChirpLogger
) {
    fun create(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            /*
             * Content Negotiation to convert the response body to a specific type.
             */
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            /*
             * Timeout configuration to prevent long-running requests from hanging indefinitely.
             */
            install(HttpTimeout) {
                socketTimeoutMillis = 20_000L
                requestTimeoutMillis = 20_000L
            }
            install(Logging) {
                logger = object: Logger {
                    override fun log(message: String) {
                        chirpLogger.debug(message)
                    }
                }
                level = LogLevel.ALL
            }
            install(WebSockets) {
                pingIntervalMillis = 20_000
            }
            /*
             * Attaches default headers to all requests.
             * Attaches api key to all requests
             */
            defaultRequest {
                header("x-api-key", "1234567890") // TODO revisit this. our API doesnt need key, maybe add it?
                contentType(ContentType.Application.Json)
            }
        }
    }
}