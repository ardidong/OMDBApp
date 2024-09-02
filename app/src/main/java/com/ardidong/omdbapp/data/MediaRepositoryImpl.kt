package com.ardidong.omdbapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.ardidong.omdbapp.data.library.db.AppDatabase
import com.ardidong.omdbapp.data.mapper.SearchMapper
import com.ardidong.omdbapp.domain.MediaRepository
import com.ardidong.omdbapp.domain.model.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val remoteMediatorFactory: MediaRemoteMediator.Factory
) : MediaRepository {

    override suspend fun hasLocalData(): Boolean {
        return appDatabase.mediaDao().countAllMedia() > 0
    }

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun searchMedia(title: String): Flow<PagingData<Media>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            remoteMediator = remoteMediatorFactory.create(title),
            pagingSourceFactory = {
                if (title.isBlank()) {
                    appDatabase.mediaDao().getAllMedia()
                } else {
                    appDatabase.mediaDao().getMedias(title)
                }
            }
        ).flow.map { pagingData ->
            pagingData.map { entity ->
                SearchMapper().toModel(entity)
            }
        }
    }

    companion object {
        const val PAGE_SIZE = 10
    }
}