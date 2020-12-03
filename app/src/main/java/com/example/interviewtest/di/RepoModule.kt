package com.example.interviewtest.di

import com.example.interviewtest.db.PostDataBase
import com.example.interviewtest.repository.MainRepo
import com.example.interviewtest.network.AppServices
import com.example.interviewtest.network.NetworkConnectionStatus
import com.example.interviewtest.repository.DBRepo
import com.example.interviewtest.repository.DBRepository
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
        networkConnectionStatus: NetworkConnectionStatus
    ) =
        MainRepo(appServices, networkConnectionStatus) as MainRepository


    @Provides
    fun dbRepoProvider(postDataBase: PostDataBase) = DBRepo(postDataBase) as DBRepository

}