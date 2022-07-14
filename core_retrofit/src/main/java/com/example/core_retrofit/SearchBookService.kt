package com.example.core_retrofit

import com.example.model.BookInfoList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchBooksService {

    /**
     * GoogleBooksAPIスキーマ
     * @link https://developers.google.com/books/docs/v1/using
     */
    @GET("v1/volumes/")
    fun searchBooks(@Query("q") keyword: String): Call<BookInfoList>

}