package com.ardidong.omdbapp.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    suspend fun searchMedia(
        title: String
    ): Flow<PagingData<Media>>
}