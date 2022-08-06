package com.example.extension.api

import retrofit2.HttpException
import retrofit2.Response

sealed interface Result<T : Any>
class ApiSuccess<T : Any>(val data: T) : Result<T>
class HttpError<T : Any>(val code: Int, val message: String) : Result<T>
class ApiException<T : Any>(val e: Throwable) : Result<T>

suspend fun <T: Any> Result<T>.onSuccess(
    executable: suspend (T) -> Unit
): Result<T> = also {
    if (it is ApiSuccess<T>) {
        executable(it.data)
    }
}

suspend fun <T: Any> Result<T>.onHttpError(
    executable: suspend (Int, String) -> Unit
): Result<T> = also {
    if (it is HttpError<T>) {
        executable(it.code, it.message)
    }
}

suspend fun <T: Any> Result<T>.onException(
    executable: suspend (Throwable) -> Unit
): Result<T> = also {
    if (it is ApiException<T>) {
        executable(it.e)
    }
}

suspend fun <T : Any> api(
    execute: suspend () -> Response<T>
): Result<T> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            ApiSuccess(data = body)
        } else {
            HttpError(code = response.code(), message = response.message())
        }
    } catch (e: HttpException) {
        HttpError(code = e.code(), message = e.message())
    } catch (e: Throwable) {
        ApiException(e)
    }
}
