package com.ardidong.omdbapp.data.library.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ardidong.omdbapp.data.db.MediaDao
import com.ardidong.omdbapp.data.db.RemoteKeyDao
import com.ardidong.omdbapp.data.db.entity.MediaEntity
import com.ardidong.omdbapp.data.db.entity.RemoteKeyEntity
import com.ardidong.omdbapp.data.library.Converters

@Database(
    entities = [
        MediaEntity::class,
        RemoteKeyEntity::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mediaDao(): MediaDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}