package com.ardidong.omdbapp.domain

enum class MediaType {
    MOVIE,
    SERIES,
    GAME,
    OTHER
}

fun String.toMediaType(): MediaType = when(this.lowercase().trim()) {
    "movie" -> MediaType.MOVIE
    "series" -> MediaType.SERIES
    "game" -> MediaType.GAME
    else -> MediaType.OTHER
}