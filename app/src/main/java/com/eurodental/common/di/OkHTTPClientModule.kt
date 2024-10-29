package com.eurodental.common.di

import com.eurodental.common.network.AuthInterceptorOkHttpClient
import com.eurodental.common.network.AuthResponseInterceptorOkHTTPClient
import com.eurodental.common.network.RequestInterceptor
import com.eurodental.common.network.ResponseInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OkHTTPClientModule {

    @Singleton
    @Provides
    fun provideOkHTTPClient(
        @AuthInterceptorOkHttpClient interceptor: RequestInterceptor,
        @AuthResponseInterceptorOkHTTPClient responseInterceptor: ResponseInterceptor,
    ) : OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(responseInterceptor)
            .build()
    }
}