package com.ardidong.omdbapp.common

sealed class ResultOf<out T> {
    data class Success<T>(val data: T): ResultOf<T>()
    data class Failure<T>(val error: ErrorEntity): ResultOf<T>()

    inline fun <V> fold(success: (T) -> V, failure: (ErrorEntity) -> V): V = when (this) {
        is Success -> success(this.data)
        is Failure -> failure(this.error)
    }
}
