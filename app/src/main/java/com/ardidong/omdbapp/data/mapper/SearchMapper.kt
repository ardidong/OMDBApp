package com.ardidong.omdbapp.data.mapper

import com.ardidong.omdbapp.data.SearchMediaResponse
import com.ardidong.omdbapp.domain.Media
import com.ardidong.omdbapp.domain.PagedData
import com.ardidong.omdbapp.common.orZero

class SearchMapper {
    fun toPagedModel(response: SearchMediaResponse, page: Int): PagedData<Media> {
        return PagedData(
            page = page,
            totalResults = response.totalResults?.toIntOrNull().orZero(),
            results = response.search?.map { item ->
                Media(
                    type = item?.type.orEmpty(),
                    year = item?.year.orEmpty(),
                    imdbID = item?.imdbID.orEmpty(),
                    poster = item?.poster.orEmpty(),
                    title = item?.title.orEmpty()
                )
            }.orEmpty()
        )
    }
}