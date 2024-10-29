package com.eurodental.features.stocks.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StockRepoModule {

    @Singleton
    @Binds
    abstract fun bindStockRepo(stockRepo: StockRepo) : StockRepoBase
}