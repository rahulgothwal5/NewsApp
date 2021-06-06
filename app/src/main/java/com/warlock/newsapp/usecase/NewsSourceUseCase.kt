package com.warlock.newsapp.usecase

import com.warlock.newsapp.model.SourceListData
import com.warlock.newsapp.network.ResultData
import com.warlock.newsapp.repository.DataRepository
import javax.inject.Inject

class NewsSourceUseCase @Inject constructor(
    private val dataRepository: DataRepository
) {
    suspend fun getNewsSourceList(): ResultData<SourceListData> {
        val newsSourceData = dataRepository.getNewsSourceList()

        return when (newsSourceData.sources.isNullOrEmpty().not()) {
            true -> {
                ResultData.Success(newsSourceData)
            }
            else -> {
                ResultData.Failed()
            }
        }
    }
}