package com.example.newsportal.app.topnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.newsportal.utils.NewsAdapter
import com.example.newsportal.R
import com.example.newsportal.data.local.model.ArticleLocal
import org.koin.androidx.viewmodel.ext.android.getViewModel

class CategoryFragment : Fragment() {

    //private val viewModel: NewsViewModel by viewModels({requireParentFragment()})
    private val viewModel: NewsViewModel by lazy { requireParentFragment().getViewModel<NewsViewModel>() }
    private val category: String by lazy { requireArguments().getString(CATEGORY_KEY, "general") }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.rv)
        val adapter = NewsAdapter()
        recyclerView.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner, {
            adapter.setData(filteredNews(it))
        })

    }

    private fun filteredNews(news: List<ArticleLocal>) =
        news.filter { it.category == category }

    companion object {
        fun newInstance(category: String): CategoryFragment {
            val fragment = CategoryFragment()
            fragment.arguments = Bundle().apply {
                putString(CATEGORY_KEY, category)
            }
            return fragment
        }
    }

}