package com.ardidong.omdbapp

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ardidong.omdbapp.data.db.RemoteKeyDao
import com.ardidong.omdbapp.data.db.entity.RemoteKeyEntity
import com.ardidong.omdbapp.data.library.db.AppDatabase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RemoteKeyDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var remoteKeyDao: RemoteKeyDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            AppDatabase::class.java
        ).build()

        remoteKeyDao = database.remoteKeyDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertKeyReturnSuccess() = runTest {
        val remoteKey = RemoteKeyEntity("searchTerm", 1)

        remoteKeyDao.insertOrReplace(remoteKey)
        val result = remoteKeyDao.remoteKeyByTerm("searchTerm")
        assertEquals(remoteKey, result)
    }

    @Test
    fun testDeleteByTermReturnNull() = runTest {
        val remoteKey = RemoteKeyEntity("searchTerm", 1)

        remoteKeyDao.insertOrReplace(remoteKey)
        remoteKeyDao.deleteByTerm("searchTerm")
        val result = remoteKeyDao.remoteKeyByTerm("searchTerm")

        assertEquals(null, result)
    }
}