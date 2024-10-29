package com.eurodental.features.auth.data

import com.eurodental.features.auth.data.models.LoginCredentials
import com.eurodental.features.auth.data.models.Tokens
import kotlinx.coroutines.flow.Flow

interface LoginRepositoryBase {

    suspend fun login(loginCredentials: LoginCredentials) : Result<Tokens>

    suspend fun isUserLoggedIn() : Flow<Boolean>
}