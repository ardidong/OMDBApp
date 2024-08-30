package com.ardidong.omdbapp.data.library.network

import com.ardidong.omdbapp.common.ErrorEntity
import com.ardidong.omdbapp.common.ResultOf
import retrofit2.HttpException
import retrofit2.Response

suspend fun<T: Any> handleApi(
    execute: suspend () -> Response<T>
): ResultOf<T> {
    return try {
        val response = execute()
        val body = response.body()

        if (response.isSuccessful && body != null){
            ResultOf.Success(body)
        } else {
            ResultOf.Failure(ErrorEntity.ApiResponseError(
                message = response.message(),
                e = HttpException(response)
            ))
        }
    }catch (e: HttpException){
        ResultOf.Failure(ErrorEntity.ApiResponseError(message = e.message(), e))
    }catch (t: Exception){
        ResultOf.Failure(ErrorEntity.ApiExceptionError(message = t.message.orEmpty(), t))
    }
}