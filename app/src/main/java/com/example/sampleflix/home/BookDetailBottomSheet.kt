package com.example.sampleflix.home

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.sampleflix.databinding.BookDetailBottomSheetBinding
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
            bookTitle = argument.title
            publishDate = argument.publishDate
            episodeNum = (1..100).random()
            bookDescription = argument.description
        }.root
    }

    // TODO リネーム
    @Parcelize
    data class BookInfoItem(
        val title: String,
        val publishDate: String,
        val description: String
    ): Parcelable
}