package com.ardidong.omdbapp

import com.ardidong.omdbapp.data.MediaApiService
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException

class MediaApiServiceTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: MediaApiService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(MediaApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun searchMovieReturnsErrorMovieNotFound() = runTest {
        val mockResponse = """
         {
            "Search": [
                {
                    "Title": "Despicable Me",
                    "Year": "2010",
                    "imdbID": "tt1323594",
                    "Type": "movie",
                    "Poster": "https://m.media-amazon.com/images/M/MV5BMTY3NjY0MTQ0Nl5BMl5BanBnXkFtZTcwMzQ2MTc0Mw@@._V1_SX300.jpg"
                },
                {
                    "Title": "Despicable Me 2",
                    "Year": "2013",
                    "imdbID": "tt1690953",
                    "Type": "movie",
                    "Poster": "https://m.media-amazon.com/images/M/MV5BMTQxNzY1MjI5NF5BMl5BanBnXkFtZTcwNTI0MDY1OQ@@._V1_SX300.jpg"
                },
                {
                    "Title": "Despicable Me 3",
                    "Year": "2017",
                    "imdbID": "tt3469046",
                    "Type": "movie",
                    "Poster": "https://m.media-amazon.com/images/M/MV5BNjUyNzQ2MTg3Ml5BMl5BanBnXkFtZTgwNzE4NDM3MTI@._V1_SX300.jpg"
                },
                {
                    "Title": "Despicable Me 4",
                    "Year": "2024",
                    "imdbID": "tt7510222",
                    "Type": "movie",
                    "Poster": "https://m.media-amazon.com/images/M/MV5BOTk4MjFhZTMtOWIxOS00YzE2LThkZGEtMzg0MDAyMmFiZmU1XkEyXkFqcGdeQXVyMTY3ODkyNDkz._V1_SX300.jpg"
                },
                {
                    "Title": "Despicable Me: Minion Madness",
                    "Year": "2010",
                    "imdbID": "tt2129997",
                    "Type": "movie",
                    "Poster": "https://m.media-amazon.com/images/M/MV5
                    BMTY4NjM2MDUxMV5BMl5BanBnXkFtZTgwMDM0NjA2MDE@._V1_SX300.jpg"
                },
                {
                    "Title": "Despicable Me: Minion Mayhem 3D",
                    "Year": "2012",
                    "imdbID": "tt2755518",
                    "Type": "movie",
                    "Poster": "https://m.media-amazon.com/images/M/MV5BMTczYzQ4OTQtNGU2Yy00YTZkLTgwZDUtNzkwMDUwYTI1NWMzXkEyXkFqcGdeQXVyNjExODE1MDc@._V1_SX300.jpg"
                },
                {
                    "Title": "Despicable Me 2: 3 Mini-Movie Collection",
                    "Year": "2014",
                    "imdbID": "tt4294236",
                    "Type": "movie",
                    "Poster": "https://m.media-amazon.com/images/M/MV5BNjAwNzA0NjAyNl5BMl5BanBnXkFtZTgwOTg2MDM4MzE@._V1_SX300.jpg"
                },
                {
                    "Title": "Despicable Me",
                    "Year": "2010",
                    "imdbID": "tt1822208",
                    "Type": "game",
                    "Poster": "https://m.media-amazon.com/images/M/MV5BNTgzMjU3MzMtZWQ0MS00MDFmLTg1OTktOGU3ODNmZGVjM2FiXkEyXkFqcGdeQXVyNjMwOTA1MDM@._V1_SX300.jpg"
                },
                {
                    "Title": "Despicable Me 2: The Music in the Film",
                    "Year": "2013",
                    "imdbID": "tt3232184",
                    "Type": "movie",
                    "Poster": "https://m.media-amazon.com/images/M/MV5BYjhlNDQwZWItMTYzZi00NTYxLWI0YzktYTYzYWRhZjA3NGYxXkEyXkFqcGdeQXVyMTE5NzI0NDM@._V1_SX300.jpg"
                },
                {
                    "Title": "Despicable Me 2: The Minions",
                    "Year": "2013",
                    "imdbID": "tt3414352",
                    "Type": "movie",
                    "Poster": "N/A"
                }
            ],
            "totalResults": "14",
            "Response": "True"
        }
        """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(mockResponse).setResponseCode(200))

        val response = apiService.searchMovie("Despicable Me", 1)

        assertEquals(true, response.isSuccessful)
        assert(response.body() != null)
        assert(response.body()?.search?.size == 10)
        assert(response.body()?.search?.first()?.title.equals("Despicable Me"))
    }

    @Test
    fun searchMovieReturnsSuccess() = runTest {
        val mockResponse = """
        {
            "Response": "False",
            "Error": "Movie not found!"
        }
        """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(mockResponse).setResponseCode(200))

        val response = apiService.searchMovie("asdfghjklxyz", 1)

        assertEquals(true, response.isSuccessful)
        assert(response.body() != null)
        assert(response.body()?.search?.size == null)
        assert(response.body()?.error.equals("Movie not found!"))
    }

    @Test
    fun searchMovieThrowTimeoutException() = runTest {
        mockWebServer.enqueue(MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE))

        try {
            apiService.searchMovie("test", 1)
        } catch (e: Exception) {
            assertTrue(e is SocketTimeoutException)
        }
    }
}