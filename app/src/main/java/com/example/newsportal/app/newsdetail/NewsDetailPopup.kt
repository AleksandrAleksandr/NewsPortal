package com.example.newsportal.app.newsdetail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.bumptech.glide.Glide
import com.example.newsportal.R
import com.example.newsportal.databinding.PopupNewsDetailBinding
import com.example.newsportal.domain.model.Article
import com.example.newsportal.utils.toFormat

class NewsDetailPopup(
    width: Int,
    height: Int,
    binding: PopupNewsDetailBinding,
    listener: NewsDetailPopupListener,
    article: Article
) : PopupWindow(binding.root, width, height, true) {

    init {
        binding.apply {
            topAppBar.setNavigationOnClickListener { listener.onBackButtonClicked() }
            topAppBar.setOnMenuItemClickListener { menuItem ->
                if (menuItem.itemId == R.id.browser) {
                    listener.onBrowserButtonClicked()
                    true
                } else false
            }
            tvTitle.text = article.title
            tvDescription.text = article.description
            tvAuthor.text = binding.root.context.getString(R.string.author_template, article.author)
            tvPublishedAt.text = article.publishedAt.take(DATE_LENGTH).toFormat()
            Glide.with(articleImage).load(article.urlToImage).error(R.drawable.ic_newspaper2)
                .centerCrop().into(articleImage)
        }
        this.animationStyle = R.style.PopupWindow
    }

    companion object {
        private const val DATE_LENGTH = 10

        fun newInstance(
            context: Context,
            container: ViewGroup,
            listener: NewsDetailPopupListener,
            article: Article
        ): NewsDetailPopup {
            val binding =
                PopupNewsDetailBinding.inflate(LayoutInflater.from(context), container, false)
            return NewsDetailPopup(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                binding,
                listener,
                article
            )
        }
    }
}

interface NewsDetailPopupListener {
    fun onBackButtonClicked()
    fun onBrowserButtonClicked()
}