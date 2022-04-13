package com.example.newsportal.app.topnews

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.newsportal.R
import com.example.newsportal.app.base.BaseFragment
import com.example.newsportal.databinding.FragmentTopHeadlineBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

const val NUM_PAGES = 7

class TopNewsFragment : BaseFragment<FragmentTopHeadlineBinding>() {

    private val viewModel: NewsViewModel by viewModel()

    override fun provideViewBinding() = FragmentTopHeadlineBinding.inflate(layoutInflater)

    override fun setupViews() {
        val pagerAdapter = PagerAdapter(childFragmentManager)
        binding.pager.adapter = pagerAdapter
        binding.tabLayout.setupWithViewPager(binding.pager)
    }


    private inner class PagerAdapter(
        fragmentManager: FragmentManager
    ) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getCount() = NUM_PAGES

        override fun getItem(position: Int): Fragment {
            val category = resources.getStringArray(R.array.categories)[position]
            return CategoryFragment.newInstance(category)
        }


        override fun getPageTitle(position: Int): CharSequence? {
            return resources.getStringArray(R.array.categories)[position]
        }
    }
}