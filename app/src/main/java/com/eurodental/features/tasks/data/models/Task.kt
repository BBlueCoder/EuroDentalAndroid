package com.eurodental.features.tasks.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Task(
    @SerializedName("task_name") val taskName : String?,
    @SerializedName("task_type") val taskType : String?,
    val description : String?,
    @SerializedName("technician_id") val technicianId : Int,
    @SerializedName("task_date") val taskDate : String?,
    val observation : String,
    @SerializedName("create_by") val createBy : Int,
    @SerializedName("client_id") val clientId : Int,
    @SerializedName("status") val status : String,
    val id : Int,
    val client : String,
    @SerializedName("client_image") val clientImage : String?,
    val technician : String,
    @SerializedName("technician_image") val technicianImage : String
) : Serializable
