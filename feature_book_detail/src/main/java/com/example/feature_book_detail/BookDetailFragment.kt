package com.example.feature_book_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.feature_book_detail.databinding.FragmentBookDetailBinding
import com.example.entity.BookInfo


class BookDetailFragment : Fragment() {

    companion object {
        fun newInstance(detailInfo: BookInfo) =
            BookDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("detail_info", detailInfo)
                }
            }
    }

    private var _binding: FragmentBookDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}