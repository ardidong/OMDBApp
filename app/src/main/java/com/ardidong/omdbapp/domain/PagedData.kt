package com.ardidong.omdbapp.domain

data class PagedData<T>(
    val page: Int,
    val results: List<T>,
    val totalResults: Int,
)
