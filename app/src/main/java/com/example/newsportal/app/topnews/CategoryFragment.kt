package com.example.newsportal.app.topnews

import androidx.core.os.bundleOf
import com.example.newsportal.app.base.BaseFragment
import com.example.newsportal.utils.NewsAdapter
import com.example.newsportal.databinding.FragmentCategoryBinding
import com.example.newsportal.domain.model.Article
import org.koin.androidx.viewmodel.ext.android.getViewModel

class CategoryFragment : BaseFragment<FragmentCategoryBinding>() {

    private val viewModel: NewsViewModel by lazy { requireParentFragment().getViewModel<NewsViewModel>() }
    private val category: String by lazy {
        requireArguments().getString(CATEGORY_KEY, "general")
    }

    override fun provideViewBinding() = FragmentCategoryBinding.inflate(layoutInflater)

    override fun setupViews() {
        val adapter = NewsAdapter()
        binding.rvNews.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner, {
            adapter.setData(filteredNews(it))
        })
    }

    private fun filteredNews(news: List<Article>) =
        news.filter { it.category == category }

    companion object {
        private const val CATEGORY_KEY = "key"

        fun newInstance(category: String): CategoryFragment {
            return CategoryFragment().apply {
                arguments = bundleOf(CATEGORY_KEY to category)
            }
        }
    }

}