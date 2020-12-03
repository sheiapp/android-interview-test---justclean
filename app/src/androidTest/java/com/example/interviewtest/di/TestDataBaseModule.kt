package com.example.interviewtest.di
import android.content.Context
import androidx.room.Room
import com.example.interviewtest.db.PostDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named

@Module
@InstallIn(ApplicationComponent::class)
object TestDataBaseModule {

    @Provides
    @Named("Test_PostDataBase")
    fun provideInMemoryDb(@ApplicationContext context:Context) =
        Room.inMemoryDatabaseBuilder(context, PostDataBase::class.java)
            .allowMainThreadQueries()
            .build()
}