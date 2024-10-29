package com.eurodental.features.auth.data

import com.eurodental.common.data.AuthConfig
import com.eurodental.common.network.ServerAPI
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Singleton
    @Binds
    abstract fun provideLoginRepo(loginRepository: LoginRepository) : LoginRepositoryBase

}