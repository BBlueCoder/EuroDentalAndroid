package com.eurodental.features.auth.data.models

import com.google.gson.annotations.SerializedName


data class Tokens(
    @SerializedName("access_token") val accessToken : String,
    @SerializedName("refresh_token") val refreshToken : String,
    val id : Int,
    val email : String,
    @SerializedName("first_name") val firstName : String?,
    @SerializedName("last_name") val lastName : String?,
    val profile : String,
    @SerializedName("profile_id") val profileId : Int
) : java.io.Serializable
