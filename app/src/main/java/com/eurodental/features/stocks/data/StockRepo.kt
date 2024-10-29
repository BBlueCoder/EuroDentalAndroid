package com.eurodental.features.stocks.data

import com.eurodental.common.network.Network.handleNetworkCall
import com.eurodental.common.network.ServerAPI
import com.eurodental.features.stocks.data.models.Product
import javax.inject.Inject

class StockRepo @Inject constructor(
    private val api: ServerAPI
)  : StockRepoBase{

    override suspend fun getAllProducts(): Result<List<Product>> {
        return try {
            val result = handleNetworkCall {
                api.getAllProducts()
            }

            Result.success(result)
        }catch (ex : Exception) {
            Result.failure(ex)
        }
    }
}