package com.warlock.newsapp.ui.fragment.news_source_listing

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.warlock.newsapp.model.NewsSource
import com.warlock.newsapp.model.SourceListData
import com.warlock.newsapp.network.ResultData
import com.warlock.newsapp.ui.fragment.base.BaseViewModel
import com.warlock.newsapp.usecase.NewsSourceUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow

class NewsSourceViewModel @ViewModelInject constructor(
    private val newsSourceUseCase: NewsSourceUseCase
) : BaseViewModel() {

    val sourceList = ArrayList<NewsSource>(arrayListOf())

    /**
     * Remote call to server to fetch News Source
     * @return LiveData<ResultData<SourceListData?>>
     */
    fun getNewsList(): LiveData<ResultData<SourceListData?>> {
        return flow {
            emit(ResultData.Loading())
            try {
                emit(newsSourceUseCase.getNewsSourceList())
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ResultData.Exception())
            }
        }.asLiveData(Dispatchers.IO)
    }
}