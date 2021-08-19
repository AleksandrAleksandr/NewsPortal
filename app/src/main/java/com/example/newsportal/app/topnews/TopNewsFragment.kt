package com.example.newsportal.app.topnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.newsportal.R
import com.example.newsportal.databinding.FragmentTopHeadlineBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

const val NUM_PAGES = 7

class TopNewsFragment : Fragment() {

    private val viewModel: NewsViewModel by viewModel()
    private var _binding: FragmentTopHeadlineBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopHeadlineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagerAdapter = PagerAdapter(childFragmentManager)
        binding.pager.adapter = pagerAdapter
        binding.tabLayout.setupWithViewPager(binding.pager)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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