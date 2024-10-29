package com.eurodental.features.settings.models

import com.google.gson.annotations.SerializedName

data class Technician(
    val id : Int,
    @SerializedName("first_name") val taskName : String?,
    @SerializedName("last_name") val taskType : String?,
    @SerializedName("phone_number") val phoneNumber : String?,
    val address : String?,
    val city : String?,
    val profile : String,
    @SerializedName("is_blocked") val isBlocked : Boolean,
    @SerializedName("image_id") val imageId : Int?,
    @SerializedName("profile_id") val profileId : Int,
    val email : String,
    @SerializedName("image_path") val imagePath : String?
)
