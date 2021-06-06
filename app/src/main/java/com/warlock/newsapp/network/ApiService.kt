package com.warlock.newsapp.network

import com.warlock.newsapp.model.HeadlinesData
import com.warlock.newsapp.model.SourceListData
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(NetworkingConstants.URL_NEWS_SOURCE)
    suspend fun getNewsSourceList(
        @Query(NetworkingConstants.PARAM_APIKEY) apiKey: String,
        @Query(NetworkingConstants.PARAM_LANGUAGE) lang: String = "en"
    ): SourceListData

    @GET(NetworkingConstants.URL_TOP_HEADLINES)
    suspend fun getTopHeadLinesList(
        @Query(NetworkingConstants.PARAM_PAGE_SIZE) pageSize: Int = 50,
        @Query(NetworkingConstants.PARAM_SOURCES) source: String?,
        @Query(NetworkingConstants.PARAM_PAGE_CATEGORY) category: String?,
        @Query(NetworkingConstants.PARAM_APIKEY) apiKey: String,
        @Query(NetworkingConstants.PARAM_LANGUAGE) lang: String = "en"
    ): HeadlinesData
}