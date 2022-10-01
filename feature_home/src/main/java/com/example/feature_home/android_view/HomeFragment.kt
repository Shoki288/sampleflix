package com.example.feature_home.android_view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.entity.BookInfo
import com.example.entity.BookInfoListResponse
import com.example.feature_home.HomeViewModel
import com.example.feature_home.R
import com.example.feature_home.databinding.FragmentHomeBinding
import com.wada811.databinding.withBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home),
    RecommendCarouselAdapter.RecommendCarouselListener {
    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        withBinding<FragmentHomeBinding> { binding ->
            binding.viewModel = viewModel
            binding.lifecycleOwner = viewLifecycleOwner


            //読み始めたシリーズを続ける
            val recentlyReadingBooksAdapter = RecommendCarouselAdapter(this, viewModel)
            binding.recentlyReadingBooksList.apply {
                carousel.adapter = recentlyReadingBooksAdapter
                showAll.setOnClickListener {
                    HomeFragmentDirections.actionNavHomeToRecommendListFragment(
                        BookInfoListResponse(viewModel.recentlyReadingBooks.value)
                    ).also { action -> findNavController() .navigate(action) }
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.recentlyReadingBooks.collect {
                        recentlyReadingBooksAdapter.submitList(it)
                    }
                }
            }

            //すぐ読める本
            val recommendBooksAdapter = RecommendCarouselAdapter(this, viewModel)
            binding.recommendList.apply {
                carousel.adapter = recommendBooksAdapter
                showAll.setOnClickListener {
                    HomeFragmentDirections.actionNavHomeToRecommendListFragment(
                        BookInfoListResponse(viewModel.recommendBooks.value)
                    ).also { action -> findNavController() .navigate(action) }
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.recommendBooks.collect {
                        recommendBooksAdapter.submitList(it)
                    }
                }
            }

            //プライム会員特定で読めるベストセラー
            val bestSellerBooksAdapter = RecommendCarouselAdapter(this, viewModel)
            binding.bestSellerList.apply {
                carousel.adapter = bestSellerBooksAdapter
                showAll.setOnClickListener {
                    HomeFragmentDirections.actionNavHomeToRecommendListFragment(
                        BookInfoListResponse(viewModel.bestSellerBooks.value)
                    ).also { action -> findNavController() .navigate(action) }
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.bestSellerBooks.collect {
                        bestSellerBooksAdapter.submitList(it)
                    }
                }
            }

            //最近読んだ本に基づくおすすめ
            val recentlyReadHistoryRecommendBooksAdapter = RecommendCarouselAdapter(this, viewModel)
            binding.recentlyReadHistoryList.apply {
                carousel.adapter = recentlyReadHistoryRecommendBooksAdapter
                showAll.setOnClickListener {
                    HomeFragmentDirections.actionNavHomeToRecommendListFragment(
                        BookInfoListResponse(viewModel.recentlyReadHistoryBooks.value)
                    ).also { action -> findNavController() .navigate(action) }
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.recentlyReadHistoryBooks.collect {
                        recentlyReadHistoryRecommendBooksAdapter.submitList(it)
                    }
                }
            }

            //もうすぐ読み放題が終了するタイトル
            val endUnlimitedReadingBooksAdapter = RecommendCarouselAdapter(this, viewModel)
            binding.endUnlimitedReadingList.apply {
                carousel.adapter = endUnlimitedReadingBooksAdapter
                showAll.setOnClickListener {
                    HomeFragmentDirections.actionNavHomeToRecommendListFragment(
                        BookInfoListResponse(viewModel.endUnlimitedReadingBooks.value)
                    ).also { action -> findNavController() .navigate(action) }
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.endUnlimitedReadingBooks.collect {
                        endUnlimitedReadingBooksAdapter.submitList(it)
                    }
                }
            }

            //近日配信開始のタイトルのおすすめ
            val recentlyReleaseBooksAdapter = RecommendCarouselAdapter(this, viewModel)
            binding.recentlyReleaseList.apply {
                carousel.adapter = recentlyReleaseBooksAdapter
                showAll.setOnClickListener {
                    HomeFragmentDirections.actionNavHomeToRecommendListFragment(
                        BookInfoListResponse(viewModel.recentlyReleaseBooks.value)
                    ).also { action -> findNavController() .navigate(action) }
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.recentlyReleaseBooks.collect {
                        recentlyReleaseBooksAdapter.submitList(it)
                    }
                }
            }

            //類似タイトルに基づくおすすめ
            val similarTitleBooksAdapter = RecommendCarouselAdapter(this, viewModel)
            binding.similarTitleList.apply {
                carousel.adapter = similarTitleBooksAdapter
                showAll.setOnClickListener {
                    HomeFragmentDirections.actionNavHomeToRecommendListFragment(
                        BookInfoListResponse(viewModel.similarTitleBooks.value)
                    ).also { action -> findNavController() .navigate(action) }
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.similarTitleBooks.collect {
                        similarTitleBooksAdapter.submitList(it)
                    }
                }
            }

            //読書履歴に基づくおすすめ
            val readingHistoryRecommendBooksAdapter = RecommendCarouselAdapter(this, viewModel)
            binding.readingHistoryList.apply {
                carousel.adapter = readingHistoryRecommendBooksAdapter
                showAll.setOnClickListener {
                    HomeFragmentDirections.actionNavHomeToRecommendListFragment(
                        BookInfoListResponse(viewModel.readingHistoryBooks.value)
                    ).also { action -> findNavController() .navigate(action) }
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.readingHistoryBooks.collect {
                        readingHistoryRecommendBooksAdapter.submitList(it)
                    }
                }
            }

            //本をさらに見る
            val categoriesAdapter = RecommendCategoryAdapter{
                findNavController().navigate(HomeFragmentDirections.actionNavHomeToSearchResultFragment(it))
            }
            binding.recommendCategoryList.carousel.adapter = categoriesAdapter
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.categories.collect {
                        categoriesAdapter.submitList(it)
                    }
                }
            }
        }
    }

    override fun onClickItem(item: BookInfo) {
        BookDetailBottomSheet.newInstance(childFragmentManager, item)
    }
}