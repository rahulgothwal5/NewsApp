package com.warlock.newsapp.ui.fragment.news_source_listing

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.warlock.newsapp.BR
import com.warlock.newsapp.R
import com.warlock.newsapp.databinding.FragmentNewsSourceListBinding
import com.warlock.newsapp.network.NetworkingConstants
import com.warlock.newsapp.network.ResultData
import com.warlock.newsapp.ui.fragment.base.BaseFragment
import com.warlock.newsapp.util.SingleLiveDataEvent
import com.warlock.newsapp.util.Toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsSourceFragment : BaseFragment<FragmentNewsSourceListBinding, NewsSourceViewModel>(),
    SwipeRefreshLayout.OnRefreshListener {

    private lateinit var mBinding: FragmentNewsSourceListBinding
    private val newsSourceViewModel: NewsSourceViewModel by viewModels()
    private lateinit var newsSourceAdapter: NewsSourceAdapter


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
        newsSourceAdapter =
            NewsSourceAdapter(newsSourceViewModel.sourceList) { item, adapterPosition ->
            }
        mBinding.rvNewsSource.adapter = newsSourceAdapter
        mBinding.swipeRefresh.setOnRefreshListener(this)
    }

    /**
     * Function to fetch remote data
     */
    private fun getDataAndSubscribeEvents() {
        val repositoriesListLiveData = newsSourceViewModel.getNewsList()
        repositoriesListLiveData.observe(viewLifecycleOwner, { resultData ->
            when (resultData) {
                is ResultData.Loading -> {
                    newsSourceViewModel.setLoading(true)
                }
                is ResultData.Success -> {
                    newsSourceViewModel.setLoading(false)
                    newsSourceViewModel.sourceList.clear()
                    newsSourceViewModel.sourceList.addAll(resultData.data?.sources ?: arrayListOf())
                    newsSourceAdapter.submitList(newsSourceViewModel.sourceList)
                }
                is ResultData.Failed -> {
                    newsSourceViewModel.setLoading(false)

                    Toast.showToast(requireActivity(),resultData.message?:"")
                    newsSourceViewModel.snackBar.postValue(
                        SingleLiveDataEvent(
                            resultData.message ?: ""
                        )
                    )
                }
                is ResultData.Exception -> {
                    newsSourceViewModel.setLoading(false)
                }
            }
        })
    }

    override fun onRefresh() {
        mBinding.swipeRefresh.isRefreshing = false
        getDataAndSubscribeEvents()
    }

    override fun getLayoutId() = R.layout.fragment_news_source_list

    override fun getBindingVariable() = BR.newsSourceVM

    override fun getViewModel() = newsSourceViewModel

    override fun onClick(v: View?) {
        when (v?.id) {
            mBinding.btnHeadlines.id -> {
                var strSource = ""

                val filteredList = newsSourceViewModel.sourceList.filter {
                    it.isSelected
                }

                for (item in filteredList) {
                    if (strSource.isEmpty()) {
                        strSource += item.id
                    } else {
                        strSource = strSource + "," + item.id
                    }
                }
                findNavController().navigate(
                    R.id.headlinesFragment,
                    bundleOf(NetworkingConstants.PARAM_SOURCES to strSource),
                )
            }

        }
    }

}