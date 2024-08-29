package com.ardidong.omdbapp.common

sealed class ErrorEntity(open val message: String) {

    class ApiResponseError(override val message: String, val errorCode: String): ErrorEntity(message)

    class ApiExceptionError(override val message: String, e: Throwable): ErrorEntity(message)

    class EmptyResultError(override val message: String): ErrorEntity(message)

    class NotFoundError(override val message: String): ErrorEntity(message)

    class Unknown(override val message: String): ErrorEntity(message)
}
