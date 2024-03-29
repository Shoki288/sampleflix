package com.example.core_retrofit

import com.example.entity.BookInfoListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchBooksService {

    /**
     * GoogleBooksAPIスキーマ
     * @link https://developers.google.com/books/docs/v1/using
     */
    @GET("v1/volumes")
    suspend fun searchBooks(
        @Query("q") keyword: String,
        @Query("maxResults") maxResults: Int? = null,
        @Query("startIndex") offset: Int = 0
    ): Response<BookInfoListResponse>

}