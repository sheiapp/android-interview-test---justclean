package com.example.interviewtest.di

import android.content.Context
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
object WorkerModule {
    @Provides
    fun provideWorker(@ApplicationContext appContext: Context): WorkManager {
        return WorkManager.getInstance(appContext)
    }
}