package com.ardidong.omdbapp.data.mapper

import com.ardidong.omdbapp.data.SearchMediaResponse
import com.ardidong.omdbapp.data.db.entity.MediaEntity
import com.ardidong.omdbapp.domain.model.Media
import com.ardidong.omdbapp.domain.toMediaType
import java.util.Date

class SearchMapper {

    fun toModel(entity: MediaEntity) : Media {
        return Media(
            type = entity.type,
            year = entity.year,
            imdbID = entity.imdbID,
            poster = entity.poster,
            title = entity.title
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