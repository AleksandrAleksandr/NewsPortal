package com.example.newsportal.app.search

import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.newsportal.R
import com.example.newsportal.app.base.BaseFragment
import com.example.newsportal.app.topnews.NewsAdapter
import com.example.newsportal.databinding.FragmentSearchNewsBinding
import com.example.newsportal.utils.constructDatePicker
import com.example.newsportal.utils.getDate
import com.example.newsportal.utils.toDate
import com.example.newsportal.utils.toFormat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

@FlowPreview
@ExperimentalCoroutinesApi
class SearchNewsFragment : BaseFragment<FragmentSearchNewsBinding>() {

    private val viewModel: NewsSearchViewModel by viewModel()
    private val adapter by lazy { NewsAdapter() }

    override fun provideViewBinding() = FragmentSearchNewsBinding.inflate(layoutInflater)

    override fun setupViews() {
        val picker =  constructDatePicker()
        viewModel.dateIntervalChanged(Pair(getDate(), getDate()))
        binding.tvDatePick.text = getString(R.string.buttonText, getDate().toFormat(), getDate().toFormat())

        picker.addOnPositiveButtonClickListener {
            val interval = Pair(it.first.toDate(), it.second.toDate())
            binding.tvDatePick.text = getString(R.string.buttonText, interval.first.toFormat(), interval.second.toFormat())
            viewModel.dateIntervalChanged(interval)
        }

        binding.tvDatePick.setOnClickListener {
            picker.show(requireActivity().supportFragmentManager, picker.toString())
        }

        binding.svQuery.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = true

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.queryTextChanged(newText)
                return true
            }
        })
        binding.rvFoundNews.adapter = adapter
    }

    override fun observeState() {
        viewModel.data.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, {
            setLoading(it)
        })

        viewModel.error.observe(viewLifecycleOwner, { message ->
            showError(message)
        })
    }

    private fun setLoading(loading: Boolean) {
        binding.progressBar.isVisible = loading
    }

    private fun showError(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }
}