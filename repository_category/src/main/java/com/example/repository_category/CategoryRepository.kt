package com.example.repository_category

import com.example.core_cache.cache_home.dao.HomeRecommendDao
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val dao: HomeRecommendDao,
) {
    suspend fun getCategory(): List<String> = dao.getAllBooksCategory()
}