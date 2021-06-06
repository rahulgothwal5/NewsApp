package com.warlock.newsapp.ui.fragment.top_headlines

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.warlock.newsapp.BR
import com.warlock.newsapp.R
import com.warlock.newsapp.databinding.FragmentHeadlinesBinding
import com.warlock.newsapp.network.ResultData
import com.warlock.newsapp.ui.fragment.base.BaseFragment
import com.warlock.newsapp.util.SingleLiveDataEvent
import com.warlock.newsapp.util.Toast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HeadlineFragment : BaseFragment<FragmentHeadlinesBinding, HeadLinesViewModel>(),
    SwipeRefreshLayout.OnRefreshListener {

    private lateinit var mBinding: FragmentHeadlinesBinding
    private val headlineVM: HeadLinesViewModel by viewModels()
    private lateinit var headlineAdapter: HeadlineAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = getViewDataBinding()
        setupView()
        getDataAndSubscribeEvents()
    }

    /**
     * Function to setup adapter and swipe refresh
     */
    private fun setupView() {
        headlineAdapter =
            HeadlineAdapter(arrayListOf()) { }
        mBinding.rvHeadlines.adapter = headlineAdapter
        mBinding.swipeRefresh.setOnRefreshListener(this)
    }

    /**
     * Function to fetch remote data
     */
    private fun getDataAndSubscribeEvents() {
        val repositoriesListLiveData = headlineVM.getHeadLines()
        repositoriesListLiveData.observe(viewLifecycleOwner, { resultData ->
            when (resultData) {
                is ResultData.Loading -> {
                    headlineVM.setLoading(true)
                }
                is ResultData.Success -> {
                    headlineVM.setLoading(false)
                    headlineAdapter.submitList(arrayListOf())
                    headlineAdapter.submitList(resultData.data?.articles ?: arrayListOf())
                }
                is ResultData.Failed -> {
                    headlineVM.setLoading(false)
                    headlineVM.snackBar.postValue(SingleLiveDataEvent("Enter a valid E-mail"))
                }
                is ResultData.Exception -> {
                    headlineVM.setLoading(false)
                }
            }
        })
    }

    override fun onRefresh() {
        mBinding.swipeRefresh.isRefreshing = false
        getDataAndSubscribeEvents()
    }

    override fun getLayoutId() = R.layout.fragment_headlines

    override fun getBindingVariable() = BR.headlinesVM

    override fun getViewModel() = headlineVM

    override fun onClick(v: View?) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> findNavController().navigateUp()
            R.id.action_sort_default -> {
                getDataAndSubscribeEvents()
                return true
            }
            R.id.action_sort_business -> {
                headlineVM.category = "business"
                getDataAndSubscribeEvents()
                return true
            }
            R.id.action_sort_entertainment -> {
                headlineVM.category = "entertainment"
                getDataAndSubscribeEvents()
                return true
            }
            R.id.action_sort_general -> {
                headlineVM.category = "general"
                getDataAndSubscribeEvents()
                return true
            }
            R.id.action_sort_health -> {
                headlineVM.category = "health"
                getDataAndSubscribeEvents()
                return true
            }
            R.id.action_sort_science -> {
                headlineVM.category = "science"
                getDataAndSubscribeEvents()
                return true
            }
            R.id.action_sort_sports -> {
                headlineVM.category = "sports"
                getDataAndSubscribeEvents()
                return true
            }
            R.id.action_sort_technology -> {
                headlineVM.category = "technology"
                getDataAndSubscribeEvents()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_sorting, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}