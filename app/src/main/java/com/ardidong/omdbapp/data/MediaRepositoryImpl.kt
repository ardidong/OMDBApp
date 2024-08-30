package com.ardidong.omdbapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.ardidong.omdbapp.data.library.db.AppDatabase
import com.ardidong.omdbapp.data.mapper.SearchMapper
import com.ardidong.omdbapp.domain.Media
import com.ardidong.omdbapp.domain.MediaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val apiService: MediaApiService,
    private val appDatabase: AppDatabase
) : MediaRepository {
    @OptIn(ExperimentalPagingApi::class)
    override suspend fun searchMedia(title: String): Flow<PagingData<Media>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            remoteMediator = MediaRemoteMediator(apiService, title, appDatabase),
            pagingSourceFactory = {
                appDatabase.mediaDao().getMedias(title)
            }
        ).flow.map { pagingData ->
            pagingData.map { entity ->
                SearchMapper().toModel(entity)
            }
        }
    }

    companion object {
        const val PAGE_SIZE = 10
        const val INITIAL_PAGE_KEY = 1
    }
}