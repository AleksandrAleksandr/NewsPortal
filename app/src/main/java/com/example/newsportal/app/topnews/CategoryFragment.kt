package com.example.newsportal.app.topnews

import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.example.newsportal.app.base.BaseFragment
import com.example.newsportal.databinding.FragmentCategoryBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel

class CategoryFragment : BaseFragment<FragmentCategoryBinding>() {

    private val viewModel: NewsViewModel by lazy { requireParentFragment().getViewModel<NewsViewModel>() }
    private val adapter by lazy { NewsAdapter() }
    private val category: String by lazy {
        requireArguments().getString(CATEGORY_KEY, "general")
    }

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

    companion object {
        private const val CATEGORY_KEY = "key"

        fun newInstance(category: String): CategoryFragment {
            return CategoryFragment().apply {
                arguments = bundleOf(CATEGORY_KEY to category)
            }
        }
    }

}