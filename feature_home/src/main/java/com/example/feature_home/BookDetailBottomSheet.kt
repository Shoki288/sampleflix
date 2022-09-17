package com.example.feature_home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.entity.BookInfo
import com.example.feature_home.databinding.BookDetailBottomSheetBinding
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
        arguments?.getParcelable(KEY_BOOK_INFO) as? BookInfo
            ?: throw IllegalArgumentException("argument class cast error")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return BookDetailBottomSheetBinding.inflate(inflater, container, false).apply {
            // TODO xmlと記述の順番を合わせる
            bookTitle = argument.bookInfo.title
            publishDate = argument.bookInfo.publishedDate
            imageUrl = argument.bookInfo.images.imageUrl
            episodeNum = (1..100).random()
            description = argument.bookInfo.description
            owner = this@BookDetailBottomSheet
        }.root
    }

    fun onClickClose() = dismiss()
    fun onClickDetail() {
        // TODO 詳細画面に飛ばす
    }
}