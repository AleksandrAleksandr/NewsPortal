package com.example.newsportal.app.topnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.newsportal.R
import com.google.android.material.tabs.TabLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

const val NUM_PAGES = 7

class TopNewsFragment : Fragment() {

    private val viewModel: NewsViewModel by viewModel()
    private lateinit var mPager: ViewPager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_top_headline, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPager = view.findViewById(R.id.pager)
        val pagerAdapter = PagerAdapter(childFragmentManager)
        mPager.adapter = pagerAdapter

        val tabLayout: TabLayout = view.findViewById(R.id.tab_layout)
        tabLayout.setupWithViewPager(mPager)
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