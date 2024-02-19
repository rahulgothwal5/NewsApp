//package com.warlock.newsapp.di
//
//import com.warlock.newsapp.repository.DataRepository
//import com.warlock.newsapp.usecase.NewsSourceUseCase
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.components.ActivityRetainedComponent
//import dagger.hilt.android.components.ViewModelComponent
//
//@Module
//@InstallIn(ViewModelComponent::class)
//object UseCaseModule {
//
//    @Provides
//    fun providesDataUseCase(dataRepository: DataRepository): NewsSourceUseCase {
//        return NewsSourceUseCase(dataRepository)
//    }
//}