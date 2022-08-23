package com.example.search_repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core_retrofit.SearchBooksService
import com.example.entity.BookInfo
import com.example.repository_favorite.use_case.GetFavoriteListUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import retrofit2.HttpException

class SearchBooksPagingSource @AssistedInject constructor(
    private val service: SearchBooksService,
    private val useCase: GetFavoriteListUseCase,
    @Assisted private val keyword: String
): PagingSource<Int, BookInfo>() {
    companion object {
        private const val LOAD_SIZE = 20
    }

    override fun getRefreshKey(state: PagingState<Int, BookInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(LOAD_SIZE) ?: anchorPage?.nextKey?.minus(LOAD_SIZE)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookInfo> {
        try {
            val offset = params.key ?: 0
            val response = service.searchBooks(keyword = keyword, maxResults = LOAD_SIZE, offset = offset)
            val body = response.body()?.let { list ->
                val ids = useCase.fetchFavoriteList().map { it.id }
                list.apply {
                    items.map { items ->
                        if (ids.any { it == items.id }) {
                            val isFavorite = items.bookInfo.isFavorite
                            items.copy(bookInfo = items.bookInfo.copy(isFavorite = isFavorite.not()))
                        } else items
                    }
                }
            } ?: return LoadResult.Error(Exception())

            val itemSize = body.items.size
            return LoadResult.Page(
                data = body.items,
                prevKey = if (offset == 1) null else offset.minus(itemSize),
                nextKey = if (itemSize < 20) null else offset.plus(itemSize),
            )
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }

    }

    @AssistedFactory
    interface SearchBooksPagingSourceFactory {
        fun create(keyword: String): SearchBooksPagingSource
    }
}