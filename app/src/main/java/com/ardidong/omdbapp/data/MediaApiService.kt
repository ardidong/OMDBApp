package com.ardidong.omdbapp.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MediaApiService {
    @GET("/")
    suspend fun searchMovie(
        @Query("s") title: String,
        @Query("page") page: Int
    ): Response<SearchMediaResponse>
}