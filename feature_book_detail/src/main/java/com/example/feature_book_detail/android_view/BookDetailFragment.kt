package com.example.feature_book_detail.android_view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.extension.view_model.assistedViewModels
import com.example.feature_book_detail.BookDetailViewModel
import com.example.feature_book_detail.R
import com.example.feature_book_detail.databinding.FragmentBookDetailBinding
import com.wada811.databinding.withBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BookDetailFragment : Fragment(R.layout.fragment_book_detail) {
    private val args by navArgs<BookDetailFragmentArgs>()

    @Inject
    lateinit var factory: BookDetailViewModel.Factory
    private val viewModel by assistedViewModels { factory.create(args.bookInfo) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        withBinding<FragmentBookDetailBinding> { binding ->
            binding.viewModel = viewModel
        }
    }
}