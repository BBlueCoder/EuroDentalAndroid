package com.eurodental.features.tasks.data

import com.eurodental.features.taskdetails.data.TaskDetails
import com.eurodental.features.tasks.data.models.Task

interface TasksRepositoryBase {

    suspend fun getAllTasks(
        exactDate: String? = null,
        dateStart: String? = null,
        dateEnd: String? = null
    ): Result<List<Task>>

    suspend fun getTaskDetails(taskId : Int) : Result<TaskDetails>
}