package com.warlock.newsapp.repository

import com.warlock.newsapp.model.HeadlinesData
import com.warlock.newsapp.model.SourceListData
import com.warlock.newsapp.network.ApiService
import javax.inject.Inject

class DataRepository @Inject constructor(private val apiService: ApiService) {

    private val API_KEY: String = "507937e3a8c34ff8926a673746a0da66"

    suspend fun getNewsSourceList(): SourceListData {
        return apiService.getNewsSourceList(apiKey = API_KEY)
    }

    suspend fun getTopHeadLines(source:String?,category:String?): HeadlinesData {
        return apiService.getTopHeadLinesList(apiKey = API_KEY ,source=source,category = category )
    }
}