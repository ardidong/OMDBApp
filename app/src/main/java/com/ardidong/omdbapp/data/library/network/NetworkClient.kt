package com.ardidong.omdbapp.data.library.network

interface NetworkClient {

    fun <T: Any> create(clazz: Class<T>) : T

}