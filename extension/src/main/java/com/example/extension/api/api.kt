package com.example.extension.api

import retrofit2.HttpException
import retrofit2.Response

sealed interface ApiResult<T : Any>
class ApiSuccess<T : Any>(val data: T) : ApiResult<T>
class ApiHttpError<T : Any>(val code: Int, val message: String) : ApiResult<T>
class ApiException<T : Any>(val e: Throwable) : ApiResult<T>

suspend fun <T: Any> ApiResult<T>.onSuccess(
    executable: suspend (T) -> Unit
): ApiResult<T> = also {
    if (it is ApiSuccess<T>) {
        executable(it.data)
    }
}

suspend fun <T: Any> ApiResult<T>.onHttpError(
    executable: suspend (Int, String) -> Unit
): ApiResult<T> = also {
    if (it is ApiHttpError<T>) {
        executable(it.code, it.message)
    }
}

suspend fun <T: Any> ApiResult<T>.onException(
    executable: suspend (Throwable) -> Unit
): ApiResult<T> = also {
    if (it is ApiException<T>) {
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
            ApiSuccess(data = body)
        } else {
            ApiHttpError(code = response.code(), message = response.message())
        }
    } catch (e: HttpException) {
        ApiHttpError(code = e.code(), message = e.message())
    } catch (e: Throwable) {
        ApiException(e)
    }
}
