package com.eurodental.features.taskdetails.data

import com.eurodental.features.clients.data.models.Client
import com.eurodental.features.settings.models.Technician
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TaskDetails(
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
    val client : Client,
    val technician : Technician
) : Serializable
