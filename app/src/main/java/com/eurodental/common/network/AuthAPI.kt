package com.eurodental.common.network

import com.eurodental.features.auth.data.models.Tokens
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AuthAPI {

    @Multipart
    @POST("login")
    suspend fun login(@Part email : MultipartBody.Part, @Part password : MultipartBody.Part) : Response<Tokens>

    @POST("refresh_token")
    suspend fun refreshToken(@Header(RequestInterceptor.AUTHORIZATION_HEADER) refreshToken : String) : Response<Tokens>
}