package com.warlock.newsapp.di

import com.warlock.newsapp.repository.DataRepository
import com.warlock.newsapp.usecase.NewsSourceUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object UseCaseModule {

    @Provides
    fun providesDataUseCase(dataRepository: DataRepository): NewsSourceUseCase {
        return NewsSourceUseCase(dataRepository)
    }
}