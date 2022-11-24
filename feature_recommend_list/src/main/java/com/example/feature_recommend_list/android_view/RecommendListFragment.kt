package com.example.feature_recommend_list.android_view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.feature_recommend_list.R
import com.example.feature_recommend_list.databinding.FragmentRecommendListBinding
import com.wada811.databinding.withBinding

class RecommendListFragment : Fragment(R.layout.fragment_recommend_list) {
    private val args by navArgs<RecommendListFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        withBinding<FragmentRecommendListBinding> { binding ->
            val adapter = RecommendItemAdapter {
                findNavController().navigate(
                    RecommendListFragmentDirections.actionRecommendListFragmentToBookDetailFragment(it)
                )
            }
            binding.list.adapter = adapter
            adapter.submitList(args.bookInfoResponse.items)
        }
    }
}