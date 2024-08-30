package com.ardidong.omdbapp.data.mapper

import com.ardidong.omdbapp.common.orZero
import com.ardidong.omdbapp.data.SearchMediaResponse
import com.ardidong.omdbapp.data.db.entity.MediaEntity
import com.ardidong.omdbapp.domain.Media
import com.ardidong.omdbapp.domain.PagedData
import com.ardidong.omdbapp.domain.toMediaType
import java.util.Date

class SearchMapper {
    fun toPagedModel(response: SearchMediaResponse, page: Int): PagedData<Media> {
        return PagedData(
            page = page,
            totalResults = response.totalResults?.toIntOrNull().orZero(),
            results = response.search?.map { item ->
                Media(
                    type = item?.type.orEmpty().toMediaType(),
                    year = item?.year.orEmpty(),
                    imdbID = item?.imdbID.orEmpty(),
                    poster = item?.poster.orEmpty(),
                    title = item?.title.orEmpty()
                )
            }.orEmpty()
        )
    }

    fun toModel(entity: MediaEntity) : Media {
        return Media(
            type = entity.type,
            year = entity.year,
            imdbID = entity.imdbID,
            poster = entity.poster,
            title = entity.title
        )
    }

    fun toEntity(model: Media, searchTerm: String, lastUpdate: Date): MediaEntity {
        return MediaEntity(
            imdbID = model.imdbID,
            type = model.type,
            title = model.title,
            poster = model.poster,
            searchTerm = searchTerm,
            lastUpdated = lastUpdate,
            year = model.year
        )
    }

    fun toEntities(response: SearchMediaResponse, searchTerm: String, lastUpdate: Date): List<MediaEntity> {
        val result = response.search
        return result?.map { item ->
            MediaEntity(
                imdbID = item?.imdbID.orEmpty(),
                type = item?.type.orEmpty().toMediaType(),
                title = item?.title.orEmpty(),
                poster = item?.poster.orEmpty(),
                searchTerm = searchTerm,
                lastUpdated = lastUpdate,
                year = item?.year.orEmpty()
            )
        }.orEmpty()
    }
}