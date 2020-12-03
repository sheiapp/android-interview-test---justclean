package com.example.interviewtest.di

import android.content.Context
import com.example.interviewtest.network.AppServices
import com.example.interviewtest.network.NetworkConnectionStatus
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    fun loggingInterceptorProvider(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }


    @Provides
    fun interceptorProvider(): Interceptor {
        return Interceptor {
            val originalRequest = it.request()
            val builder =
                originalRequest.newBuilder()
                    .addHeader("Content-Type", "application/json")
            val newRequest = builder.build()
            it.proceed(newRequest)
        }
    }

    @Provides
    fun converterFactoryProvider(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun okHttpClientProvider(
        interceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
            addInterceptor(httpLoggingInterceptor)
        }
        return okHttpClient.build()
    }

    @Provides
    fun retrofitProvider(
        converterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        val retrofit = Retrofit.Builder().apply {
            baseUrl("https://jsonplaceholder.typicode.com")
            addConverterFactory(converterFactory)
            client(okHttpClient)
        }
        return retrofit.build()
    }

    @Provides
    fun appServiceProvider(retrofit: Retrofit): AppServices {
        return retrofit.create(AppServices::class.java)
    }

    @Provides
    fun networkConnectionStatusProvider(@ApplicationContext appContext: Context):NetworkConnectionStatus{
        return NetworkConnectionStatus(appContext)
    }
}