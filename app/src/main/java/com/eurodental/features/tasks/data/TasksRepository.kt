package com.eurodental.features.tasks.data

import android.util.Log
import com.eurodental.common.network.Network.handleNetworkCall
import com.eurodental.common.network.ServerAPI
import com.eurodental.features.taskdetails.data.TaskDetails
import com.eurodental.features.tasks.data.models.Task
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TasksRepository @Inject constructor(private val api : ServerAPI) : TasksRepositoryBase{
    private var cachedData : List<Task> = emptyList()

    override suspend fun getAllTasks(
        exactDate: String?,
        dateStart: String?,
        dateEnd: String?
    ): Result<List<Task>> {
        Log.d("DateTab","exact date =$exactDate dateStart = $dateStart enddate = $dateEnd")
        try {
//            if (cachedData.isNotEmpty())
//                return Result.success(cachedData)
            val result = handleNetworkCall {
                api.getAllTasks(exactDate,dateStart,dateEnd)
            }
            cachedData = result
            return Result.success(result)
        }catch (ex : Exception) {
            return Result.failure(ex)
        }
    }

    override suspend fun getTaskDetails(taskId: Int): Result<TaskDetails> {
        Log.d("TaskDetail","receive id = $taskId")
        return try {
            val result = handleNetworkCall {
                api.getTaskDetailsById(taskId)
            }

            Result.success(result)
        }catch (ex : Exception) {
            Result.failure(ex)
        }
    }


}