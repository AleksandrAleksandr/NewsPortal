package com.example.newsportal.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.newsportal.NewsAdapter
import com.example.newsportal.R
import com.example.newsportal.model.ArticleLocal
import com.example.newsportal.viewmodel.NewsViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class CategoryFragment : Fragment() {

    //private val viewModel: NewsViewModel by viewModels({requireParentFragment()})
    private val viewModel: NewsViewModel by lazy { requireParentFragment().getViewModel<NewsViewModel>() }
    lateinit var category: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf { it.containsKey("key") }?.apply {
            category = getString("key", "sports")

            val recyclerView: RecyclerView = view.findViewById(R.id.rv)
            val adapter = NewsAdapter()
            recyclerView.adapter = adapter

            viewModel.data.observe(viewLifecycleOwner, {
                adapter.setData(filteredNews(it))
            })
        }
    }

    private fun filteredNews(news: List<ArticleLocal>) =
        news.filter { it.category == category }

}