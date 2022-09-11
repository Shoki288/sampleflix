package com.example.feature_favorite

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.entity.adeapter.cacheBookInfoAdapter
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
            val adapter = FavoriteListAdapter()
            binding.list.adapter = adapter
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.favoriteList.collectLatest {
                        binding.emptyTitle.isVisible = it.isEmpty()
                        adapter.submitList(cacheBookInfoAdapter(it).items)
                    }
                }
            }
        }
    }
}