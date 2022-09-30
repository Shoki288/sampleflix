package com.example.feature_search.android_view

import android.graphics.Rect
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.feature_search.R
import com.example.feature_search.SearchViewModel
import com.example.feature_search.databinding.FragmentSearchBinding
import com.wada811.databinding.withBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val viewModel by viewModels<SearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        withBinding<FragmentSearchBinding> { binding ->
            // 検索窓
            binding.textField.setOnKeyListener { _, code, event ->
                if (event.action == KeyEvent.ACTION_DOWN && code == KeyEvent.KEYCODE_ENTER) {
                    val keyword =
                        binding.textField.editText?.text?.toString() ?: return@setOnKeyListener true
                    val action =
                        SearchFragmentDirections.actionNavSearchToSearchResultFragment(keyword)
                    findNavController().navigate(action)
                    return@setOnKeyListener false
                }
                return@setOnKeyListener true
            }

            // カテゴリリスト
            val adapter = CategoryAdapter{
                val action = SearchFragmentDirections.actionNavSearchToSearchResultFragment(it)
                findNavController().navigate(action)
            }
            binding.categoryList.let {
                it.adapter = adapter
                it.addItemDecoration(itemOffset)
            }
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.categories.collect {
                        adapter.submitList(it)
                    }
                }
            }
        }

        hideKeyboard()
    }

    private fun hideKeyboard() {
//        binding.textField.setOnFocusChangeListener { v, hasFocus ->
//            if (hasFocus.not()) {
//                val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                imm.hideSoftInputFromWindow(v.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
//            }
//        }
//
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                if (binding.textField.hasFocus()) {
//                    binding.textField.clearFocus()
//                }
//            }
//        })
    }

    private val itemOffset = object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.set(8, 6, 8, 6)
        }
    }

}
