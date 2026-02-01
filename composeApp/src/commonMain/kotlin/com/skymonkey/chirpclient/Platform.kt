package com.skymonkey.chirpclient

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform