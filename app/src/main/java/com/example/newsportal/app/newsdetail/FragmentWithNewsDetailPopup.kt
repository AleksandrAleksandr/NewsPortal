package com.example.newsportal.app.newsdetail

import android.view.Gravity
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.example.newsportal.domain.model.Article

interface FragmentWithNewsDetailPopup {

    var newsDetailPopup: NewsDetailPopup?

    fun showNewsDetailPopup(article: Article, activity: FragmentActivity, viewGroup: ViewGroup) {
        if (newsDetailPopup == null) {
            newsDetailPopup = NewsDetailPopup.newInstance(
                activity,
                viewGroup,
                object : NewsDetailPopupListener {
                    override fun onBackButtonClicked() {
                        closeNewsDetailPopup()
                    }

                    override fun onBrowserButtonClicked() {

                    }
                },
                article
            ).apply {
                showAtLocation(viewGroup, Gravity.CENTER, 0, 0)
                setOnDismissListener {
                    newsDetailPopup?.run { closeNewsDetailPopup() }
                }
            }
        }
    }

    fun closeNewsDetailPopup() {
        val popup = newsDetailPopup
        newsDetailPopup = null
        popup?.dismiss()
    }
}