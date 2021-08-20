package com.example.newsportal.app.topnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.newsportal.utils.NewsAdapter
import com.example.newsportal.databinding.FragmentCategoryBinding
import com.example.newsportal.domain.model.Article
import com.example.newsportal.utils.ResultWrapper
import org.koin.androidx.viewmodel.ext.android.getViewModel

class CategoryFragment : Fragment() {

    private val viewModel: NewsViewModel by lazy { requireParentFragment().getViewModel<NewsViewModel>() }
    private val category: String by lazy { requireArguments().getString(CATEGORY_KEY, "general") }
    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NewsAdapter()
        binding.rvNews.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner, { status ->
            when(status){
                is ResultWrapper.Success -> adapter.setData(filteredNews(status.data)).also { setLoading(false) }
                is ResultWrapper.Error -> {
                    binding.tv.text = status.msg
                    setLoading(false)
                }
                ResultWrapper.Loading -> setLoading(true)
            }
        })

    }

    private fun filteredNews(news: List<Article>) =
        news.filter { it.category == category }

    private fun setLoading(loading: Boolean){
        binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
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