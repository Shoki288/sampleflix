package com.example.feature_book_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.feature_book_detail.databinding.FragmentBookDetailBinding
import com.wada811.databinding.withBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookDetailFragment : Fragment(R.layout.fragment_book_detail) {
    private val viewModel by viewModels<BookDetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        withBinding<FragmentBookDetailBinding> { binding ->
            binding.viewModel = viewModel
        }
    }
}