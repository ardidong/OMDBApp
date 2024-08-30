package com.ardidong.omdbapp.common

import retrofit2.HttpException

sealed class ErrorEntity(open val message: String, open val e: Exception) {

    class ApiResponseError(override val message: String, override val e: HttpException): ErrorEntity(message, e)

    class ApiExceptionError(override val message: String, override val e: Exception): ErrorEntity(message,e )
}
