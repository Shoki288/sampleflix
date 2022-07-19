package com.example.extension.api

import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

class ApiZipSuccess<T: Any>(val data: Pair<T, T>): ApiResult<T>

suspend fun <T: Any> ApiResult<T>.onZipSuccess(
    executable: suspend (first: T, second: T) -> Unit
): ApiResult<T> = also {
    if (it is ApiZipSuccess<T>) {
        executable(it.data.first, it.data.second)
    }
}

suspend fun <T : Any> zip(
    firstExecute: suspend () -> Response<T>,
    secondExecute: suspend () -> Response<T>,
    coroutineContext: CoroutineContext
): ApiResult<T> {
    return try {
        withContext(coroutineContext) {
            val first = async { firstExecute() }
            val second = async { secondExecute() }

            val (res1, res2) = Pair(first.await(), second.await())
            val (body1, body2) = Pair(res1.body(), res2.body())

            if (res1.isSuccessful && body1 != null && res2.isSuccessful && body2 != null) {
                ApiZipSuccess(data = Pair(body1, body2))
            } else {
                ApiHttpError(code = res1.code(), message = res1.message())
            }
        }
    } catch (e: HttpException) {
        ApiHttpError(code = e.code(), message = e.message())
    } catch (e: Throwable) {
        ApiException(e)
    }
}