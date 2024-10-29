package com.eurodental.common.data

import android.util.Log
import androidx.collection.emptyIntSet
import androidx.datastore.preferences.core.stringPreferencesKey
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.io.encoding.Base64

@Singleton
class AuthConfig @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager
) {

    companion object {
        private const val ACCESS_TOKEN_KEY = "ACCESS_TOKEN"
        private const val REFRESH_TOKEN_KEY = "REFRESH_TOKEN"
    }

    fun storeTokens(accessToken: String, refreshToken: String) {
        sharedPreferencesManager.save(ACCESS_TOKEN_KEY, accessToken)
        sharedPreferencesManager.save(REFRESH_TOKEN_KEY, refreshToken)
    }

    fun isUserLoggedIn(): Boolean {
        Log.d("AuthConfig", "retrieving data")

        val accessToken = retrieveAccessToken()
        val refreshToken = retrieveRefreshToken()
        Log.d("AuthConfig", "access token = $accessToken")
        Log.d("AuthConfig", "refresh token = $refreshToken")
        if (accessToken == null && refreshToken == null)
            return false

        accessToken?.let {
            val claimsFromAccessToken = decodeJWT(it)
            if (claimsFromAccessToken != null) {
                return true
            }
        }

        refreshToken?.let {
            val claimsFromAccessToken = decodeJWT(it)
            if (claimsFromAccessToken != null) {
                return true
            }
        }

        return false
    }

    private fun decodeJWT(token: String): Claims? {
        try {
            val keyBytes =
                "637d7ae22429851c08a0846ba4a6b908d693585e949ceacf60316eeb2d539158".toByteArray()
            val key = Keys.hmacShaKeyFor(keyBytes)

            val claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)

            return claims.payload
        } catch (ex: Exception) {
            ex.printStackTrace()
            Log.e("AuthConfig", "${ex.message}")
            return null
        }
    }

    fun retrieveAccessToken(): String? {
        return sharedPreferencesManager.retrieve(ACCESS_TOKEN_KEY)
    }

    fun retrieveRefreshToken(): String? {
        return sharedPreferencesManager.retrieve(REFRESH_TOKEN_KEY)
    }
}