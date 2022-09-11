package com.example.feature_home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.entity.BookInfo
import com.example.feature_home.databinding.FragmentHomeBinding
import com.wada811.databinding.withBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), RecommendCarouselAdapter.RecommendCarouselListener {
    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        withBinding<FragmentHomeBinding> { binding ->
            binding.viewModel = viewModel
            binding.lifecycleOwner = viewLifecycleOwner


            //読み始めたシリーズを続ける
            val recentlyReadingBooksAdapter = RecommendCarouselAdapter(this)
            binding.recentlyReadingBooksList.carousel.adapter = recentlyReadingBooksAdapter
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.recentlyReadingBooks.collect {
                        recentlyReadingBooksAdapter.submitList(convertRecommendItem(it))
                    }
                }
            }

            //すぐ読める本
            val recommendBooksAdapter = RecommendCarouselAdapter(this)
            binding.recommendList.carousel.adapter = recommendBooksAdapter
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.recommendBooks.collect {
                        recommendBooksAdapter.submitList(convertRecommendItem(it))
                    }
                }
            }

            //プライム会員特定で読めるベストセラー
            val bestSellerBooksAdapter = RecommendCarouselAdapter(this)
            binding.bestSellerList.carousel.adapter = bestSellerBooksAdapter
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.bestSellerBooks.collect {
                        bestSellerBooksAdapter.submitList(convertRecommendItem(it))
                    }
                }
            }

            //最近読んだ本に基づくおすすめ
            val recentlyReadHistoryRecommendBooksAdapter = RecommendCarouselAdapter(this)
            binding.recentlyReadHistoryList.carousel.adapter = recentlyReadHistoryRecommendBooksAdapter
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.recentlyReadHistoryBooks.collect {
                        recentlyReadHistoryRecommendBooksAdapter.submitList(convertRecommendItem(it))
                    }
                }
            }

            //もうすぐ読み放題が終了するタイトル
            val endUnlimitedReadingBooksAdapter = RecommendCarouselAdapter(this)
            binding.endUnlimitedReadingList.carousel.adapter = endUnlimitedReadingBooksAdapter
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.endUnlimitedReadingBooks.collect {
                        endUnlimitedReadingBooksAdapter.submitList(convertRecommendItem(it))
                    }
                }
            }

            //近日配信開始のタイトルのおすすめ
            val recentlyReleaseBooksAdapter = RecommendCarouselAdapter(this)
            binding.recentlyReleaseList.carousel.adapter = recentlyReleaseBooksAdapter
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.recentlyReleaseBooks.collect {
                        recentlyReleaseBooksAdapter.submitList(convertRecommendItem(it))
                    }
                }
            }

            //類似タイトルに基づくおすすめ
            val similarTitleBooksAdapter = RecommendCarouselAdapter(this)
            binding.similarTitleList.carousel.adapter = similarTitleBooksAdapter
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.similarTitleBooks.collect {
                        similarTitleBooksAdapter.submitList(convertRecommendItem(it))
                    }
                }
            }

            //読書履歴に基づくおすすめ
            val readingHistoryRecommendBooksAdapter = RecommendCarouselAdapter(this)
            binding.readingHistoryList.carousel.adapter = readingHistoryRecommendBooksAdapter
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.readingHistoryBooks.collect {
                        readingHistoryRecommendBooksAdapter.submitList(convertRecommendItem(it))
                    }
                }
            }

            //本をさらに見る
            val categoriesAdapter = RecommendCategoryAdapter()
            binding.recommendCategoryList.carousel.adapter = categoriesAdapter
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.categories.collect {
                        categoriesAdapter.submitList(it)
                    }
                }
            }

            binding.similarTitleList.title.setOnClickListener {  }
        }
    }

    private fun convertRecommendItem(list: List<BookInfo>) =
        list.map {
            RecommendItem(
                id = it.id,
                title = it.bookInfo.title,
                publishDate = it.bookInfo.publishedDate ?: "2022",
                description = it.bookInfo.description,
                imgUrl = it.bookInfo.images?.imageUrl ?: ""
            )
        }

    override fun onClickItem(item: BookDetailBottomSheet.BookInfoItem) =
        BookDetailBottomSheet.newInstance(childFragmentManager, item)
}