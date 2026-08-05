package com.cobasendiri.kasirmudahkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform