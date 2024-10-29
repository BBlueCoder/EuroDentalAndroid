package com.eurodental.common.di

import android.content.Context
import com.eurodental.common.data.AuthConfig
import com.eurodental.common.data.DataStoreManager
import com.eurodental.common.data.SharedPreferencesManager
import com.eurodental.common.network.AuthAPI
import com.eurodental.common.network.ServerAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    private const val SERVER_API_BASE_URL = "http://35.180.66.24/api/v1/"

    @Singleton
    @Provides
    fun provideApplicationAPI(okHttpClient: OkHttpClient) : ServerAPI {
        return Retrofit.Builder()
            .baseUrl(SERVER_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ServerAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthAPI() : AuthAPI {
        return Retrofit.Builder()
            .baseUrl(SERVER_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideDataStoreManager(@ApplicationContext context : Context) : DataStoreManager {
        return DataStoreManager(context)
    }

    @Singleton
    @Provides
    fun provideAuthConfig(sharedPreferencesManager: SharedPreferencesManager) : AuthConfig {
        return AuthConfig(sharedPreferencesManager)
    }
}