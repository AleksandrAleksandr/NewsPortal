package com.example.newsportal.app.bookmarks

import androidx.recyclerview.widget.ItemTouchHelper
import com.example.newsportal.app.base.BaseFragment
import com.example.newsportal.app.bookmarks.touchhelper.ItemTouchHelperCallback
import com.example.newsportal.app.newsdetail.FragmentWithNewsDetailPopup
import com.example.newsportal.app.newsdetail.NewsDetailPopup
import com.example.newsportal.databinding.FragmentBookmarksBinding
import com.example.newsportal.domain.model.Article
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookmarksFragment: BaseFragment<FragmentBookmarksBinding>(), FragmentWithNewsDetailPopup {

    private val bookmarksViewModel: BookmarksViewModel by viewModel()
    private val adapter by lazy { BookmarksAdapter(this::onArticleSelected, this::onItemSwiped) }
    override var newsDetailPopup: NewsDetailPopup? = null

    override fun provideViewBinding() = FragmentBookmarksBinding.inflate(layoutInflater)

    override fun setupViews() {
        binding.rvBookmarks.adapter = adapter
        val callback: ItemTouchHelper.Callback = ItemTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.rvBookmarks)
    }

    override fun observeState() {
        bookmarksViewModel.bookmarks.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    private fun onArticleSelected(article: Article) {
        showNewsDetailPopup(article, requireActivity(), binding.root)
    }

    private fun onItemSwiped(article: Article) {
        bookmarksViewModel.onArticleSwiped(article)
    }



}