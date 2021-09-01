package com.example.newsportal.app.bookmarks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsportal.R
import com.example.newsportal.app.bookmarks.touchhelper.ItemTouchHelperAdapter
import com.example.newsportal.app.topnews.NewsDiffCallback
import com.example.newsportal.databinding.ListItemBinding
import com.example.newsportal.domain.model.Article

class BookmarksAdapter(
    val onItemClick: (Article) -> Unit,
    val onItemSwiped: (Article) -> Unit
) : ListAdapter<Article, BookmarksAdapter.ItemViewHolder>(NewsDiffCallback),
    ItemTouchHelperAdapter {

    inner class ItemViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            with(article) {
                binding.btnBookmark.visibility = View.GONE
                binding.itemTitle.text = title
                Glide.with(binding.root).load(urlToImage).error(R.drawable.ic_newspaper2)
                    .centerCrop().into(binding.itemImage)
                binding.itemTitle.setOnClickListener { onItemClick(this) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onItemDismiss(position: Int) {
        onItemSwiped(getItem(position))
    }
}



