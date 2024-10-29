package com.eurodental.common.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException

object Network {

    suspend fun <T> handleNetworkCall(
        networkCall : suspend () -> retrofit2.Response<T>
    ): T {
        return withContext(Dispatchers.IO) {
            val resp = networkCall()

            if(!resp.isSuccessful) {
                throw IOException("Network Call failed!")
            }

            if(resp.body() == null)
                throw IOException("Response body is empty")

            resp.body()!!
        }
    }
}