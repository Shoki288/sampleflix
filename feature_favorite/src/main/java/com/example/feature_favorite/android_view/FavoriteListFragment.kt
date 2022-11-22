package com.example.feature_favorite.android_view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.entity.adeapter.cacheBookInfoAdapter
import com.example.feature_favorite.FavoriteListViewModel
import com.example.feature_favorite.R
import com.example.feature_favorite.databinding.FragmentFavoriteListBinding
import com.wada811.databinding.withBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteListFragment : Fragment(R.layout.fragment_favorite_list) {
    private val viewModel by viewModels<FavoriteListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        withBinding<FragmentFavoriteListBinding> { binding ->
            binding.viewModel = viewModel

            // お気に入りリスト
            val adapter = FavoriteListAdapter (
                onClickFavorite = { item, isFavorite ->
                    viewModel.updateFavoriteState(item, isFavorite)
                },
                onClickItem = {
                    findNavController().navigate(
                        FavoriteListFragmentDirections.actionNavFavoriteToBookDetailFragment(it)
                    )
                }
            )
            binding.list.adapter = adapter
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.favoriteList.collectLatest {
                        when(it) {
                            is FavoriteListUiState.Success -> adapter.submitList(cacheBookInfoAdapter(it.bookInfoList).items)
                            is FavoriteListUiState.Error -> binding.errorFrame.text = it.message
                            else -> { /** nop **/ }
                        }
                    }
                }
            }
        }
    }
}