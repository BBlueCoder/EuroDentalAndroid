package com.eurodental.common.network

import android.util.Log
import com.eurodental.common.data.AuthConfig
import com.eurodental.features.auth.data.models.Tokens
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class ResponseInterceptor @Inject constructor(
        private val authAPI: AuthAPI,
    private val authConfig: AuthConfig
) : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if(response.code == 401) {
            Log.d("Interceptor","Response returns 401!")
            val _refreshToken = authConfig.retrieveRefreshToken()
            Log.d("Interceptor","refresh token = $_refreshToken")

            if(_refreshToken != null) {
                val tokens = refreshToken(_refreshToken)
                if(tokens != null) {
                    Log.d("Interceptor","tokens return not null with value $tokens")

                    val newRequest = chain.request().newBuilder()
                    newRequest.removeHeader(RequestInterceptor.AUTHORIZATION_HEADER)
                    newRequest.addHeader(RequestInterceptor.AUTHORIZATION_HEADER, "Bearer ${tokens.accessToken}")
                    response.close()
                    return chain.proceed(newRequest.build())
                }
            }
        }

        return response
    }

    private fun refreshToken(refreshToken : String) : Tokens? {
        return runBlocking {
            Log.d("Interceptor","sending request to refresh token!")
            val resp = authAPI.refreshToken("Bearer $refreshToken")
            Log.d("Interceptor","isresponse success ${resp.isSuccessful}")

            if (resp.isSuccessful && resp.body() != null) {
                Log.d("Interceptor","retrieved body successfully")

                authConfig.storeTokens(resp.body()!!.accessToken, resp.body()!!.refreshToken)
                return@runBlocking resp.body()
            }

            return@runBlocking null
        }
    }
}