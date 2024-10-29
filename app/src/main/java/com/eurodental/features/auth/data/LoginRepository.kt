package com.eurodental.features.auth.data

import com.eurodental.common.data.AuthConfig
import com.eurodental.common.data.DataStoreManager
import com.eurodental.common.network.Network.handleNetworkCall
import com.eurodental.common.network.ServerAPI
import com.eurodental.features.auth.data.models.LoginCredentials
import com.eurodental.features.auth.data.models.Tokens
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okio.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.log

@Singleton
class LoginRepository @Inject constructor(
    private val api : ServerAPI,
    private val authConfig: AuthConfig
): LoginRepositoryBase {

    override suspend fun login(loginCredentials: LoginCredentials) : Result<Tokens> {
        try {
            val resultOfNetworkCall = handleNetworkCall {
                val email = MultipartBody.Part.createFormData(
                    name = "username",
                    value = loginCredentials.email
                )
                val password = MultipartBody.Part.createFormData(
                    name = "password",
                    value = loginCredentials.password
                )
                api.login(email,password)
            }
            authConfig.storeTokens(resultOfNetworkCall.accessToken,resultOfNetworkCall.refreshToken)
            return Result.success(resultOfNetworkCall)
        }catch (ex : Exception) {
            return Result.failure(ex)
        }
    }

    override suspend fun isUserLoggedIn() : Flow<Boolean> = flow {
        emit(authConfig.isUserLoggedIn())
    }


}