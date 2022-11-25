package com.example.feature_home.android_view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.example.entity.BookInfo
import com.example.extension.getNotNullParcelable
import com.example.feature_home.databinding.BookDetailBottomSheetBinding
import com.example.feature_search_result.android_view.SearchResultFragmentDirections
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BookDetailBottomSheet: BottomSheetDialogFragment() {
    companion object {
        fun newInstance(
            fragmentManager: FragmentManager,
            bookInfoItem: BookInfo
        ) = BookDetailBottomSheet().apply {
            arguments = Bundle().also {
                it.putParcelable(KEY_BOOK_INFO, bookInfoItem)
            }
        }.show(fragmentManager, BookDetailBottomSheet::class.java.simpleName)

        private const val KEY_BOOK_INFO = "book_info"
    }

    private val argument by lazy {
        arguments.getNotNullParcelable<BookInfo>(KEY_BOOK_INFO)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return BookDetailBottomSheetBinding.inflate(inflater, container, false).apply {
            bookTitle = argument.volumeInfo.title
            publishDate = argument.volumeInfo.publishedDate
            imageUrl = argument.volumeInfo.images.imageUrl
            episodeNum = (1..100).random()
            description = argument.volumeInfo.description
            owner = this@BookDetailBottomSheet
        }.root
    }

    fun onClickClose() = dismiss()
    fun onClickDetail() {
        val action = SearchResultFragmentDirections.actionSearchResultFragmentToBookDetailFragment(argument)
        findNavController().navigate(action)
    }
}