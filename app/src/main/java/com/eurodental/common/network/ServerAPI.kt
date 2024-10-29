package com.eurodental.common.network

import com.eurodental.features.auth.data.models.LoginCredentials
import com.eurodental.features.auth.data.models.Tokens
import com.eurodental.features.stocks.data.models.Product
import com.eurodental.features.taskdetails.data.TaskDetails
import com.eurodental.features.tasks.data.models.Task
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ServerAPI {

    @Multipart
    @POST("login")
    suspend fun login(
        @Part email: MultipartBody.Part,
        @Part password: MultipartBody.Part
    ): Response<Tokens>

    @POST("refresh_token")
    suspend fun refreshToken(): Response<Tokens>

    @GET("tasks")
    suspend fun getAllTasks(
        @Query("exact_date") exactDate: String? = null,
        @Query("date_range_start") dateStart: String? = null,
        @Query("date_range_end") dateEnd: String? = null
    ): Response<List<Task>>

    @GET("tasks/{task_id}")
    suspend fun getTaskById(@Path("task_id") taskId : Int) : Response<Task>

    @GET("tasks/task_details/{task_id}")
    suspend fun getTaskDetailsById(@Path("task_id") taskId: Int) : Response<TaskDetails>

    @GET("products")
    suspend fun getAllProducts() : Response<List<Product>>

}