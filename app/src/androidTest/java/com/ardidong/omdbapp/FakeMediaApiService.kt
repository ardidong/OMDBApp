package com.ardidong.omdbapp

import com.ardidong.omdbapp.data.MediaApiService
import com.ardidong.omdbapp.data.SearchMediaResponse
import retrofit2.Response

class FakeMediaApiService : MediaApiService {
    var returnValue: Response<SearchMediaResponse> = DEFAULT
    var errorException : Exception? = null

    override suspend fun searchMovie(title: String, page: Int): Response<SearchMediaResponse> {
        return if (errorException != null) throw errorException as Exception
        else returnValue
    }

    companion object{
        val DEFAULT: Response<SearchMediaResponse> = Response.success(
            SearchMediaResponse(
                response = "True",
                search = listOf(),
                totalResults = "0",
                error = null
            )
        )

        val DUMMY = SearchMediaResponse.SearchItem(
            title = "Test Movie",
            year = "2021",
            imdbID = "tt1234567",
            type = "movie",
            poster = "https://example.com/poster.jpg"
        )
    }
}