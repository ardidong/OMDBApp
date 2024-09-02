package com.ardidong.omdbapp.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ardidong.omdbapp.data.db.entity.MediaEntity

@Dao
interface MediaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MediaEntity>)

    @Query("SELECT * FROM medias WHERE searchTerm = :searchTerm")
    fun getMedias(searchTerm: String): PagingSource<Int, MediaEntity>

    @Query("SELECT * FROM medias")
    fun getAllMedia(): PagingSource<Int, MediaEntity>

    @Query("DELETE FROM medias WHERE searchTerm = :searchTerm")
    suspend fun clearMoviesForSearchTerm(searchTerm: String)

    @Query("SELECT COUNT(*) FROM medias")
    suspend fun countAllMedia(): Int
}