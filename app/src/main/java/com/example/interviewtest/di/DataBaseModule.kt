package com.example.interviewtest.di

import android.content.Context
import androidx.room.Room
import com.example.interviewtest.db.PostDataBase
import com.example.interviewtest.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun providePostDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, PostDataBase::class.java, DATABASE_NAME).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun providePostDao(
        database: PostDataBase
    ) = database.getPostDao()


}