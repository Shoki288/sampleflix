package com.example.feature_home

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.feature_home.databinding.BookDetailBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.parcelize.Parcelize

class BookDetailBottomSheet: BottomSheetDialogFragment() {
    companion object {
        fun newInstance(
            fragmentManager: FragmentManager,
            bookInfoItem: BookInfoItem
        ) = BookDetailBottomSheet().apply {
            arguments = Bundle().also {
                it.putParcelable(KEY_BOOK_INFO, bookInfoItem)
            }
        }.show(fragmentManager, BookDetailBottomSheet::class.java.simpleName)

        private const val KEY_BOOK_INFO = "book_info"
    }

    private val argument by lazy {
        arguments?.getParcelable(KEY_BOOK_INFO) as? BookInfoItem
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
            bookTitle = argument.title
            publishDate = argument.publishDate
            imageUrl = argument.imageUrl
            episodeNum = (1..100).random()
            description = argument.description
            owner = this@BookDetailBottomSheet
        }.root
    }

    fun onClickClose() = dismiss()
    fun onClickDetail() {
        // TODO 詳細画面に飛ばす
    }

    // TODO リネーム
    @Parcelize
    data class BookInfoItem(
        val title: String,
        val publishDate: String,
        val imageUrl: String,
        val description: String
    ): Parcelable
}