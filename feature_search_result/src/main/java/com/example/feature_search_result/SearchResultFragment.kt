package com.example.feature_search_result

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.feature_search_result.databinding.FragmentSearchResultBinding
import com.wada811.databinding.withBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchResultFragment : Fragment(R.layout.fragment_search_result) {

    private val args by navArgs<SearchResultFragmentArgs>()

    private val viewModel by viewModels<SearchResultViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        withBinding<FragmentSearchResultBinding> { binding ->
            binding.viewModel = viewModel

            // 検索窓
            binding.textField.editText?.setText(args.keyword)
            binding.textField.setOnKeyListener { _, code, event ->
                if (event.action == KeyEvent.ACTION_DOWN && code == KeyEvent.KEYCODE_ENTER) {
                    val keyword =
                        binding.textField.editText?.text?.toString() ?: return@setOnKeyListener true
//                viewModel.search(keyword)
                    return@setOnKeyListener false
                }
                return@setOnKeyListener true
            }

            // 検索結果のリスト
            val adapter = SearchResultAdapter(
                onClickItem = {
                    val action = SearchResultFragmentDirections.actionSearchResultFragmentToBookDetailFragment(it)
                    findNavController().navigate(action)
                },
                onClickFavorite = { bookInfo, isCheck ->

                }
            )
            binding.searchResultList.adapter = adapter
            binding.searchResultList.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.searchResult.collectLatest {
                        adapter.submitData(it)
                    }
                }
            }
        }
    }
}