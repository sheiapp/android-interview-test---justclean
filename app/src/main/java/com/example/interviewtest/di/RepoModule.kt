package com.example.interviewtest.di

import com.example.interviewtest.db.PostDataBase
import com.example.interviewtest.repository.MainRepo
import com.example.interviewtest.network.AppServices
import com.example.interviewtest.network.NetworkConnectionStatus
import com.example.interviewtest.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepoModule {

    @Provides
    fun mainRepoProvider(
        appServices: AppServices,
        postDataBase: PostDataBase,
        networkConnectionStatus: NetworkConnectionStatus
    ) =
        MainRepo(appServices, postDataBase, networkConnectionStatus) as MainRepository
}