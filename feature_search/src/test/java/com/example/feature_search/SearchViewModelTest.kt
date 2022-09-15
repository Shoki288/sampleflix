package com.example.feature_search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core_unit_test.MainCoroutineScopeRule
import com.example.core_unit_test.asLivedataObserver
import com.example.repository_category.usecase.GetAllCategoryUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    private lateinit var viewModel: SearchViewModel
    @MockK
    private lateinit var useCase: GetAllCategoryUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `init_カテゴリ情報の一覧を表示させること`() {
        coEvery { useCase.getCategory() } returns (0..10).map { "category$it" }
        viewModel = SearchViewModel(useCase = useCase)
        val observer = viewModel.categories.asLivedataObserver()

        verify {
            observer.onChanged((0..10).map { "category$it" })
        }
    }
}