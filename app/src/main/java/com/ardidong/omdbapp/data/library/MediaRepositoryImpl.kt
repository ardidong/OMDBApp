package com.ardidong.omdbapp.data.library

import com.ardidong.omdbapp.common.ResultOf
import com.ardidong.omdbapp.data.MediaApiService
import com.ardidong.omdbapp.data.library.network.handleApi
import com.ardidong.omdbapp.data.mapper.SearchMapper
import com.ardidong.omdbapp.domain.Media
import com.ardidong.omdbapp.domain.MediaRepository
import com.ardidong.omdbapp.domain.PagedData
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val apiService: MediaApiService
) : MediaRepository {
    override suspend fun searchMedia(title: String, page: Int): ResultOf<PagedData<Media>> {
        val mapper = SearchMapper()
        return handleApi {
            apiService.searchMovie(title, page)
        }.fold(
            success = { response ->
                ResultOf.Success(mapper.toPagedModel(response, page))
            },
            failure = { error ->
                ResultOf.Failure(error)
            }
        )
    }
}