package com.example.repository_category.usecase

import com.example.repository_category.CategoryRepository
import javax.inject.Inject

class GetAllCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend fun getCategory() = repository.getCategory().filterNot { it.isEmpty() }.distinct()
}