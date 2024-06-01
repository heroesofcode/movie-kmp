package io.heroesofcode

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform