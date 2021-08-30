package com.example.newsportal.app.newsdetail

import androidx.navigation.fragment.navArgs
import com.example.newsportal.app.base.BaseFragment
import com.example.newsportal.databinding.FragmentNewsDetailBinding
import com.squareup.picasso.Picasso

class NewsDetailFragment : BaseFragment<FragmentNewsDetailBinding>() {

    private val args: NewsDetailFragmentArgs by navArgs()

    override fun provideViewBinding() = FragmentNewsDetailBinding.inflate(layoutInflater)

    override fun setupViews() {
        Picasso.get().load(args.article.urlToImage).into(binding.ivImage)
    }
}