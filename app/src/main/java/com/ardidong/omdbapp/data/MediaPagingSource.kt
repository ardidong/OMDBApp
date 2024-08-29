package com.ardidong.omdbapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ardidong.omdbapp.data.library.network.handleApi
import com.ardidong.omdbapp.data.mapper.SearchMapper
import com.ardidong.omdbapp.domain.Media

class MediaPagingSource(
    private val apiService: MediaApiService,
    private val title: String
) : PagingSource<Int, Media>() {
    override fun getRefreshKey(state: PagingState<Int, Media>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Media> {
        return try {
            val currentPageNumber = params.key ?: 1

            val result = handleApi {
                apiService.searchMovie(title, currentPageNumber)
            }.fold(
                success = {
                    val mapper = SearchMapper()
                    mapper.toPagedModel(it, currentPageNumber)
                },
                failure = { return LoadResult.Error(RuntimeException()) }
            )

            val nextKey = if (result.totalResults / 10 > currentPageNumber) {
                currentPageNumber + 1
            } else null

            return LoadResult.Page(
                prevKey = if (currentPageNumber == 1) null else currentPageNumber - 1,
                nextKey = nextKey,
                data = result.results
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}