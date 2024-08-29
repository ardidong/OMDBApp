package com.ardidong.omdbapp.data.library.network

interface NetworkClient {

    suspend fun <T: Any> create(clazz: Class<T>) : T

}