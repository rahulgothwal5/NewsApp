package com.warlock.newsapp.usecase

import com.warlock.newsapp.model.HeadlinesData
import com.warlock.newsapp.network.ResultData
import com.warlock.newsapp.repository.DataRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class TopHeadLinesUseCase @Inject constructor(
    private val dataRepository: DataRepository
) {
    suspend fun getHeadlines(source: String?, category: String?): ResultData<HeadlinesData> {

        var paramSource: String? = null
        var paramCategory: String? = null

        if (category.isNullOrBlank().not()) {
            paramCategory = category
        }
        if (source.isNullOrBlank().not() and category.isNullOrEmpty()) {
            paramSource = source
        }

        val newsSourceData =
            dataRepository.getTopHeadLines(source = paramSource, category = paramCategory)

        return when (newsSourceData.articles.isNullOrEmpty().not()) {
            true -> {
                ResultData.Success(newsSourceData)
            }
            else -> {
                ResultData.Failed()
            }
        }
    }
}


