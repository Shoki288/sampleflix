package com.example.extension.api

import retrofit2.HttpException
import retrofit2.Response

sealed interface ApiResult<T : Any>
class Success<T : Any>(val data: T) : ApiResult<T>
class HttpError<T : Any>(val code: Int, val message: String) : ApiResult<T>
class Exception<T : Any>(val e: Throwable) : ApiResult<T>

suspend fun <T: Any> ApiResult<T>.onSuccess(
    executable: suspend (T) -> Unit
): ApiResult<T> = also {
    if (it is Success<T>) {
        executable(it.data)
    }
}

suspend fun <T: Any> ApiResult<T>.onHttpError(
    executable: suspend (Int, String) -> Unit
): ApiResult<T> = also {
    if (it is HttpError<T>) {
        executable(it.code, it.message)
    }
}

suspend fun <T: Any> ApiResult<T>.onException(
    executable: suspend (Throwable) -> Unit
): ApiResult<T> = also {
    if (it is Exception<T>) {
        executable(it.e)
    }
}

suspend fun <T : Any> api(
    execute: suspend () -> Response<T>
): ApiResult<T> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            Success(data = body)
        } else {
            HttpError(code = response.code(), message = response.message())
        }
    } catch (e: HttpException) {
        HttpError(code = e.code(), message = e.message())
    } catch (e: Throwable) {
        Exception(e)
    }
}
