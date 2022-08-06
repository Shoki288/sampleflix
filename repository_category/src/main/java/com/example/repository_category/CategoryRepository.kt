package com.example.repository_category

import com.example.core_cache.dao.BookInfoDao
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val dao: BookInfoDao,
) {
    suspend fun getCategory(): List<String> = dao.getAllBooksCategory()
}