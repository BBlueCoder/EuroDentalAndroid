package com.eurodental.common.network

import android.util.Log
import com.eurodental.common.data.AuthConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class RequestInterceptor @Inject constructor(
    private val authConfig: AuthConfig
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = authConfig.retrieveAccessToken()

        Log.d("Interceptor","access token = $accessToken")
        val request = chain.request().newBuilder()

        val headers = chain.request().headers
        if(headers[AUTHORIZATION_HEADER] == null) {
            accessToken?.let {
                request.addHeader(
                    AUTHORIZATION_HEADER, "Bearer $it"
                )
            }
        }

        return chain.proceed(request.build())
    }

    companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
    }
}