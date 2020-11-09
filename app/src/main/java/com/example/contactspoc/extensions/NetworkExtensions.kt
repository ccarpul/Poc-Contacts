package com.example.contactspoc.extensions

import okhttp3.Response
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(block: suspend () -> T): ResultWrapper<T> =

    try {
        val response = block()
        if (response is Response && !response.isSuccessful) GenericError(null, response.message())
        else Success(response)
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> GenericError(null, "IO Exceptión ${throwable.message} ")
            is HttpException -> NetworkError(throwable)
            else -> GenericError(null, "Error: ${throwable.message}")
        }
    }

sealed class ResultWrapper<out T>
data class Success<out T>(val value: T) : ResultWrapper<T>()
data class GenericError(val code: Int? = null, val error: String? = null) :
    ResultWrapper<Nothing>()

data class NetworkError<out T>(val throwable: HttpException) : ResultWrapper<T>()
