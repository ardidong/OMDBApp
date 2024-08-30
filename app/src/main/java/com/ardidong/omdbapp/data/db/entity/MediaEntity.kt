package com.ardidong.omdbapp.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ardidong.omdbapp.domain.MediaType
import java.util.Date

@Entity(tableName = "medias")
data class MediaEntity(
    @PrimaryKey val imdbID: String,
    val type: MediaType,
    val title: String,
    val year: String,
    val poster: String,
    val searchTerm: String,
    val lastUpdated: Date
)