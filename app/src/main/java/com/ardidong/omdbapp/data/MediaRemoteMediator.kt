package com.ardidong.omdbapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ardidong.omdbapp.common.orZero
import com.ardidong.omdbapp.data.db.entity.MediaEntity
import com.ardidong.omdbapp.data.db.entity.RemoteKeyEntity
import com.ardidong.omdbapp.data.library.db.AppDatabase
import com.ardidong.omdbapp.data.library.network.handleApi
import com.ardidong.omdbapp.data.mapper.SearchMapper
import okio.IOException
import retrofit2.HttpException
import java.util.Date

@OptIn(ExperimentalPagingApi::class)
class MediaRemoteMediator(
    private val apiService: MediaApiService,
    private val searchTerm: String,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, MediaEntity>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, MediaEntity>): MediatorResult {
        return try {

            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = appDatabase.remoteKeyDao().remoteKeyByTerm(searchTerm)
                    if (remoteKey.nextKey == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }
                    remoteKey.nextKey
                }
            }

            val response = handleApi { apiService.searchMovie(searchTerm, page) }.fold(
                success = { it },
                failure = { return MediatorResult.Error(it.e) }
            )

            val isEndOfPagination = (response.search?.size.orZero() < state.config.pageSize) ||
                    (page * 10 >= response.totalResults?.toIntOrNull().orZero())

            with(appDatabase) {
                withTransaction {
                    val entities = SearchMapper().toEntities(response, searchTerm, Date())
                    if (loadType == LoadType.REFRESH) {
                        mediaDao().clearMoviesForSearchTerm(searchTerm)
                        remoteKeyDao().deleteByTerm(searchTerm)
                    }

                    val nextPage = if (isEndOfPagination) null else page + 1
                    remoteKeyDao().insertOrReplace(
                        RemoteKeyEntity(searchTerm, nextPage)
                    )

                    mediaDao().insertAll(entities)
                }
            }

            MediatorResult.Success(endOfPaginationReached = isEndOfPagination)
        }  catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}