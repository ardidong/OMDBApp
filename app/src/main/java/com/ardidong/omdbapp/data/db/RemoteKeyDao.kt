package com.ardidong.omdbapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ardidong.omdbapp.data.db.entity.RemoteKeyEntity

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: RemoteKeyEntity)

    @Query("SELECT * FROM remote_keys WHERE label = :searchTerm")
    suspend fun remoteKeyByTerm(searchTerm: String): RemoteKeyEntity?

    @Query("DELETE FROM remote_keys WHERE label = :searchTerm")
    suspend fun deleteByTerm(searchTerm: String)
}