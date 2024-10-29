package com.eurodental.features.stocks.data

import com.eurodental.features.stocks.data.models.Product

interface StockRepoBase {

    suspend fun getAllProducts() : Result<List<Product>>
}