package com.ardidong.omdbapp.domain

import androidx.paging.PagingData
import com.ardidong.omdbapp.domain.model.Media
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    suspend fun searchMedia(
        title: String
    ): Flow<PagingData<Media>>
}