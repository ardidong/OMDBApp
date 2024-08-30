package com.ardidong.omdbapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ardidong.omdbapp.data.db.entity.MediaEntity

@Dao
interface MediaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MediaEntity>)

    @Query("SELECT * FROM medias WHERE searchTerm LIKE '%' || :searchTerm || '%' LIMIT 10")
    fun getMedias(searchTerm: String): List<MediaEntity>
}