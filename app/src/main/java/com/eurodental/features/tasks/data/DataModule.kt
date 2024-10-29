package com.eurodental.features.tasks.data

import com.eurodental.common.network.ServerAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideTasksRepo(api : ServerAPI) : TasksRepositoryBase {
        return TasksRepository(api)
    }
}