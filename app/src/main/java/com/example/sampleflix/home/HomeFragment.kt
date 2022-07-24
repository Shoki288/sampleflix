package com.example.sampleflix.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.model.BookInfo
import com.example.sampleflix.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel by viewModels<HomeViewModel>()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //読み始めたシリーズを続ける
        val recentlyReadingBooksAdapter = RecommendCarouselAdapter()
        binding.recentlyReadingBooksList.carousel.adapter = recentlyReadingBooksAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.recentlyReadingBooks.collect {
                    recentlyReadingBooksAdapter.submitList(convertRecommendItem(it))
                }
            }
        }

        //すぐ読める本
        val recommendBooksAdapter = RecommendCarouselAdapter()
        binding.recommendList.carousel.adapter = recommendBooksAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.recommendBooks.collect {
                    recommendBooksAdapter.submitList(convertRecommendItem(it))
                }
            }
        }

        //プライム会員特定で読めるベストセラー
        val bestSellerBooksAdapter = RecommendCarouselAdapter()
        binding.bestSellerList.carousel.adapter = bestSellerBooksAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.bestSellerBooks.collect {
                    bestSellerBooksAdapter.submitList(convertRecommendItem(it))
                }
            }
        }

        //最近読んだ本に基づくおすすめ
        val recentlyReadHistoryRecommendBooksAdapter = RecommendCarouselAdapter()
        binding.recentlyReadHistoryList.carousel.adapter = recentlyReadHistoryRecommendBooksAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.recentlyReadHistoryBooks.collect {
                    recentlyReadHistoryRecommendBooksAdapter.submitList(convertRecommendItem(it))
                }
            }
        }

        //もうすぐ読み放題が終了するタイトル
        val endUnlimitedReadingBooksAdapter = RecommendCarouselAdapter()
        binding.endUnlimitedReadingList.carousel.adapter = endUnlimitedReadingBooksAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.endUnlimitedReadingBooks.collect {
                    endUnlimitedReadingBooksAdapter.submitList(convertRecommendItem(it))
                }
            }
        }

        //近日配信開始のタイトルのおすすめ
        val recentlyReleaseBooksAdapter = RecommendCarouselAdapter()
        binding.recentlyReleaseList.carousel.adapter = recentlyReleaseBooksAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.recentlyReleaseBooks.collect {
                    recentlyReleaseBooksAdapter.submitList(convertRecommendItem(it))
                }
            }
        }

        //類似タイトルに基づくおすすめ
        val similarTitleBooksAdapter = RecommendCarouselAdapter()
        binding.similarTitleList.carousel.adapter = similarTitleBooksAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.similarTitleBooks.collect {
                    similarTitleBooksAdapter.submitList(convertRecommendItem(it))
                }
            }
        }

        //読書履歴に基づくおすすめ
        val readingHistoryRecommendBooksAdapter = RecommendCarouselAdapter()
        binding.readingHistoryList.carousel.adapter = readingHistoryRecommendBooksAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.readingHistoryBooks.collect {
                    readingHistoryRecommendBooksAdapter.submitList(convertRecommendItem(it))
                }
            }
        }

        // TODO このカテゴリ用のAdapterつくる
        //本をさらに見る
//        val categoriesAdapter = RecommendCarouselAdapter()
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.categories.collect {
//                    categoriesAdapter.submitList(it)
//                }
//            }
//        }
    }

    private fun convertRecommendItem(list: List<BookInfo>) =
        list.map {
            RecommendItem(
                id = it.id,
                imgUrl = it.bookInfo.images?.smallImg ?: ""
            )
        }
}