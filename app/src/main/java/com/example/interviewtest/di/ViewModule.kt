package com.example.interviewtest.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext


@Module
@InstallIn(ActivityRetainedComponent::class)
class ViewModule {

    @Provides
    fun initGlide(@ApplicationContext appContext: Context): RequestManager {
        val options = RequestOptions()
        return Glide.with(appContext)
            .setDefaultRequestOptions(options)
    }
}