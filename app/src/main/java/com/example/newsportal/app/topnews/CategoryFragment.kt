package com.example.newsportal.app.topnews

import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.example.newsportal.app.base.BaseFragment
import com.example.newsportal.app.newsdetail.NewsDetailPopup
import com.example.newsportal.app.newsdetail.FragmentWithNewsDetailPopup
import com.example.newsportal.databinding.FragmentCategoryBinding
import com.example.newsportal.domain.model.Article
import org.koin.androidx.viewmodel.ext.android.getViewModel

class CategoryFragment : BaseFragment<FragmentCategoryBinding>(), FragmentWithNewsDetailPopup {

    private val viewModel: NewsViewModel by lazy { requireParentFragment().getViewModel<NewsViewModel>() }
    private val adapter by lazy { NewsAdapter(this::onArticleSelected, this::onBookmark) }
    private val category: String by lazy {
        requireArguments().getString(CATEGORY_KEY, "general")
    }
    override var newsDetailPopup: NewsDetailPopup? = null

    override fun provideViewBinding() = FragmentCategoryBinding.inflate(layoutInflater)

    override fun setupViews() {
        binding.rvNews.adapter = adapter
    }

    override fun observeState() {
        viewModel.newsByCategory(category).observe(viewLifecycleOwner, { data ->
            adapter.submitList(data)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, {
            setLoading(it)
        })

        viewModel.error.observe(viewLifecycleOwner, { message ->
            showError(message)
        })
    }

    private fun setLoading(loading: Boolean) {
        binding.progressBar.isVisible = loading
    }

    private fun showError(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }

    private fun onArticleSelected(article: Article) {
        showNewsDetailPopup(article, requireActivity(), binding.root)
    }

    private fun onBookmark(article: Article) {
        viewModel.bookmarkSelected(article)
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