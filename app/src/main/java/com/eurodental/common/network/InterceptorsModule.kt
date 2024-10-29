package com.eurodental.common.network

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthInterceptorOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthResponseInterceptorOkHTTPClient

@Module
@InstallIn(SingletonComponent::class)
abstract class InterceptorsModule {

    @AuthInterceptorOkHttpClient
    @Binds
    abstract fun provideAuthInterceptor(interceptor: RequestInterceptor) : RequestInterceptor

    @AuthResponseInterceptorOkHTTPClient
    @Binds
    abstract fun provideAuthResponseInterceptor(interceptor: ResponseInterceptor) : ResponseInterceptor
}