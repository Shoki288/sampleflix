package com.example.extension.api

import kotlinx.coroutines.Deferred
import retrofit2.HttpException
import retrofit2.Response

class ApiZipSuccess<T : Any>(val data: Pair<T, T>) :
    Result<T>

suspend fun <T : Any> Result<T>.onZipSuccess(
    executable: suspend (first: T, second: T) -> Unit
): Result<T> = also {
    if (it is ApiZipSuccess<T>) {
        executable(it.data.first, it.data.second)
    }
}

suspend fun <T : Any> zip(
    firstExecute: suspend () -> Deferred<Response<T>>,
    secondExecute: suspend () -> Deferred<Response<T>>,
): Result<T> {
    return try {
        val res1 = firstExecute().await()
        val res2 = secondExecute().await()
        val body1 = res1.body()
        val body2 = res2.body()

        if (res1.isSuccessful && body1 != null && res2.isSuccessful && body2 != null) {
            ApiZipSuccess(data = Pair(body1, body2))
        } else {
            HttpError(code = res1.code(), message = res1.message())
        }
    } catch (e: HttpException) {
        HttpError(code = e.code(), message = e.message())
    } catch (e: Throwable) {
        ApiException(e)
    }
}