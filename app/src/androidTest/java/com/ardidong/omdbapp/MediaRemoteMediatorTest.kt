package com.ardidong.omdbapp

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ardidong.omdbapp.common.ApiErrorException
import com.ardidong.omdbapp.data.MediaRemoteMediator
import com.ardidong.omdbapp.data.SearchMediaResponse
import com.ardidong.omdbapp.data.db.entity.MediaEntity
import com.ardidong.omdbapp.data.library.db.AppDatabase
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Response
import java.net.SocketTimeoutException


@RunWith(AndroidJUnit4::class)
class MediaRemoteMediatorTest {
    private val apiService = FakeMediaApiService()
    private val appDatabase = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        AppDatabase::class.java
    ).build()
    private lateinit var remoteMediator: MediaRemoteMediator

    @Before
    fun setup() {
        remoteMediator = MediaRemoteMediator(
            apiService = apiService,
            appDatabase = appDatabase,
            searchTerm = "test"
        )
    }

    @After
    fun takeDown() {
        appDatabase.clearAllTables()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsNotPresent() = runTest {
        apiService.returnValue = Response.success(
            SearchMediaResponse(
                response = "True",
                search = mutableListOf<SearchMediaResponse.SearchItem?>().apply {
                    repeat(10) {
                        add(FakeMediaApiService.DUMMY)
                    }
                },
                error = null,
                totalResults = "10"
            )
        )

        val pagingState = PagingState<Int, MediaEntity>(listOf(), 1, PagingConfig(10), 0)
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        assert(result is RemoteMediator.MediatorResult.Success)
        assert((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest {
        apiService.returnValue = Response.success(
            SearchMediaResponse(
                response = "True",
                search = mutableListOf<SearchMediaResponse.SearchItem?>().apply {
                    repeat(20) {
                        add(FakeMediaApiService.DUMMY)
                    }
                },
                error = null,
                totalResults = "20"
            )
        )

        val pagingState = PagingState<Int, MediaEntity>(listOf(), 1, PagingConfig(10), 0)
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        assert(result is RemoteMediator.MediatorResult.Success)
        assert(!(result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun refreshLoadReturnsErrorWhenApiResponseIsFalse() = runTest {
        val errorMessage = "Movie not found!"
        apiService.returnValue = Response.success(
            SearchMediaResponse(
                response = "False",
                search = null,
                error = errorMessage,
                totalResults = null
            )
        )

        val pagingState = PagingState<Int, MediaEntity>(listOf(), 1, PagingConfig(10), 0)
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        assert(result is RemoteMediator.MediatorResult.Error)
        assert((result as RemoteMediator.MediatorResult.Error).throwable is ApiErrorException)
        assert((result.throwable as ApiErrorException).message == errorMessage)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun refreshLoadReturnsErrorResultWhenErrorOccurs() = runTest {
        apiService.errorException = SocketTimeoutException()
        val pagingState = PagingState<Int, MediaEntity>(listOf(), 1, PagingConfig(10), 0)
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assert(result is RemoteMediator.MediatorResult.Error)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun appendLoadReturnsSuccessResultWhenMoreDataIsNotPresent() = runTest {
        apiService.returnValue = Response.success(
            SearchMediaResponse(
                response = "True",
                search = mutableListOf<SearchMediaResponse.SearchItem?>().apply {
                    repeat(10) {
                        add(FakeMediaApiService.DUMMY)
                    }
                },
                error = null,
                totalResults = "20"
            )
        )

        val pagingState = PagingState<Int, MediaEntity>(
            pages = listOf(),
            anchorPosition = 1,
            config = PagingConfig(pageSize = 10),
            leadingPlaceholderCount = 0
        )


        remoteMediator.load(LoadType.REFRESH, pagingState)
        val result = remoteMediator.load(LoadType.APPEND, pagingState)

        assert(result is RemoteMediator.MediatorResult.Success)
        assert((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun appendLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest {
        apiService.returnValue = Response.success(
            SearchMediaResponse(
                response = "True",
                search = mutableListOf<SearchMediaResponse.SearchItem?>().apply {
                    repeat(10) {
                        add(FakeMediaApiService.DUMMY)
                    }
                },
                error = null,
                totalResults = "30"
            )
        )

        val pagingState = PagingState<Int, MediaEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 10),
            leadingPlaceholderCount = 0
        )


        remoteMediator.load(LoadType.REFRESH, pagingState)
        val result = remoteMediator.load(LoadType.APPEND, pagingState)

        assert(result is RemoteMediator.MediatorResult.Success)
        assert(!(result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

}