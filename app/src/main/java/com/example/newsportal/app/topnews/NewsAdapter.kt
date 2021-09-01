package com.example.newsportal.app.topnews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsportal.R
import com.example.newsportal.databinding.ListItemBinding
import com.example.newsportal.domain.model.Article

class NewsAdapter(val onItemClick: (Article) -> Unit, val onBookmarkClick: (Article) -> Unit) :
    ListAdapter<Article, NewsAdapter.ItemViewHolder>(NewsDiffCallback) {

    inner class ItemViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            with(article) {
                binding.itemTitle.text = title
                Glide.with(binding.root).load(urlToImage).error(R.drawable.ic_newspaper2)
                    .centerCrop().into(binding.itemImage)
                binding.itemTitle.setOnClickListener { onItemClick(this) }
                if (isBookmarked) {
                    binding.btnBookmark.setImageResource(R.drawable.ic_bookmark_filled)
                }

                binding.btnBookmark.setOnClickListener {
                    binding.btnBookmark.setImageResource(
                        if (isBookmarked) R.drawable.ic_bookmark
                        else R.drawable.ic_bookmark_filled
                    )
                    onBookmarkClick(this)
                }
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
}

object NewsDiffCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

}