package com.ardidong.omdbapp.domain

import com.ardidong.omdbapp.common.ResultOf

interface MediaRepository {
    suspend fun searchMedia(
        title: String,
        page: Int
    ): ResultOf<PagedData<Media>>
}