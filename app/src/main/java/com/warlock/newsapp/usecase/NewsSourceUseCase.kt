package com.warlock.newsapp.usecase

import com.warlock.newsapp.model.SourceListData
import com.warlock.newsapp.network.ResultData
import com.warlock.newsapp.repository.DataRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
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