package com.example.newsportal.app.topnews

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
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
            binding.apply {
                itemTitle.text = article.title
                Glide.with(root).load(article.urlToImage).error(R.drawable.ic_newspaper2)
                    .centerCrop().into(itemImage)
                itemView.setOnClickListener { onItemClick(article) }
                if (article.isBookmarked) {
                    bookmarkBtn.visibility = View.VISIBLE
                }
                else {
                    bookmarkBtn.visibility = View.GONE
                }

                bookmarkBtn.setOnClickListener {
                    updateBookmarkIcon(bookmarkBtn, article)
                    onBookmarkClick(article)
                }

                itemMenu.setOnClickListener { onMenuClicked(article, itemMenu) }
            }
        }

        private fun onMenuClicked(article: Article, anchor: View) {
            PopupMenu(binding.root.context, anchor).apply {
                inflate(R.menu.news_card_menu)

                with(binding.root.context) {
                    this@apply.menu.findItem(R.id.add_to_bookmark).title =
                        if (article.isBookmarked) getString(R.string.remove_to_bookmarks)
                        else getString(R.string.add_to_bookmarks)
                }

                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.view_in_browser -> {
                            viewInBrowser(binding.root.context, article)
                            true
                        }
                        R.id.add_to_bookmark -> {
                            updateBookmarkIcon(binding.bookmarkBtn, article)
                            onBookmarkClick(article)
                            true
                        }
                        else -> false
                    }
                }
                show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun viewInBrowser(context: Context, article: Article) {
        Intent(Intent.ACTION_VIEW, Uri.parse(article.url)).also { context.startActivity(it) }
    }

    fun updateBookmarkIcon(bookmark: ImageView, article: Article) {
        bookmark.visibility = if (article.isBookmarked) View.GONE else View.VISIBLE
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