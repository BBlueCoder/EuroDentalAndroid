package com.eurodental.features.clients.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Client(
    val id : Int,
    @SerializedName("first_name") val firstName : String?,
    @SerializedName("last_name") val lastName : String?,
    @SerializedName("phone_number") val phoneNumber : String?,
    val address : String?,
    val city : String?,
    val description : String?,
    @SerializedName("fixed_phone_number") val fixedPhoneNumber : String?,
    @SerializedName("image_id") val imageId : Int?,
    val email : String,
    @SerializedName("image_path") val imagePath : String?
) : Serializable
