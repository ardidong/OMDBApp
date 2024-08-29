package com.ardidong.omdbapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ardidong.omdbapp.domain.Media
import com.ardidong.omdbapp.domain.MediaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val apiService: MediaApiService
) : MediaRepository {
    override suspend fun searchMedia(title: String): Flow<PagingData<Media>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            initialKey  = INITIAL_PAGE_KEY,
            pagingSourceFactory = {
                MediaPagingSource(apiService, title)
            }
        ).flow
    }

    companion object {
        const val PAGE_SIZE = 10
        const val INITIAL_PAGE_KEY = 1
    }
}