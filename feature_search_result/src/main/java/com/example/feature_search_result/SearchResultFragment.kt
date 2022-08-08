package com.example.feature_search_result

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.feature_search_result.adapter.convertSearchResultToBookInfo
import com.example.feature_search_result.databinding.FragmentSearchResultBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchResultFragment : Fragment() {

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<SearchResultFragmentArgs>()

    private val viewModel by viewModels<SearchResultViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false).also {
            it.viewModel = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = SearchResultAdapter {
            viewModel.findBookInfoById(it)?.let { info ->
                val action = SearchResultFragmentDirections.actionSearchResultFragmentToBookDetailFragment(info)
                findNavController().navigate(action)
            }
        }
        binding.searchResultList.adapter = adapter
        binding.searchResultList.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchResult.collect {
                    if (it is SearchResultUiState.Success) {
                        adapter.submitList(convertSearchResultToBookInfo(it.results))
                    }
                }
            }
        }

        binding.textField.editText?.setText(args.keyword)
        binding.textField.setOnKeyListener { _, code, event ->
            if (event.action == KeyEvent.ACTION_DOWN && code == KeyEvent.KEYCODE_ENTER) {
                val keyword = binding.textField.editText?.text?.toString() ?: return@setOnKeyListener true
                viewModel.search(keyword)
                return@setOnKeyListener false
            }
            return@setOnKeyListener true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}