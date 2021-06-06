package com.warlock.newsapp.ui.fragment.top_headlines

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import com.warlock.newsapp.model.HeadlinesData
import com.warlock.newsapp.network.NetworkingConstants
import com.warlock.newsapp.network.ResultData
import com.warlock.newsapp.ui.fragment.base.BaseViewModel
import com.warlock.newsapp.usecase.TopHeadLinesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow

class HeadLinesViewModel @ViewModelInject constructor(
    private val topHeadLinesUseCase: TopHeadLinesUseCase,
    @Assisted savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val source = savedStateHandle.get<String>(NetworkingConstants.PARAM_SOURCES)
    var category = ""

    /**
     * Remote call to the server to fetch Headlines
     * @return LiveData<ResultData<HeadlinesData?>>
     */
    fun getHeadLines(): LiveData<ResultData<HeadlinesData?>> {
        return flow {
            emit(ResultData.Loading())
            try {
                emit(topHeadLinesUseCase.getHeadlines(source, category))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ResultData.Exception())
            }
        }.asLiveData(Dispatchers.IO)
    }
}