package com.example.newsportal.app.topnews

import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.example.newsportal.app.base.BaseFragment
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

        viewModel.data.observe(viewLifecycleOwner, { data ->
            adapter.submitList(filteredNews(data))
        })

        viewModel.isLoading.observe(viewLifecycleOwner, {
            setLoading(it)
        })

        viewModel.error.observe(viewLifecycleOwner, { message ->
            showError(message)
        })
    }

    private fun filteredNews(news: List<Article>) =
        news.filter { it.category == category }

    private fun setLoading(loading: Boolean) {
        binding.progressBar.isVisible = loading
    }

    private fun showError(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val CATEGORY_KEY = "key"

        fun newInstance(category: String): CategoryFragment {
            return CategoryFragment().apply {
                arguments = bundleOf(CATEGORY_KEY to category)
            }
        }
    }

}