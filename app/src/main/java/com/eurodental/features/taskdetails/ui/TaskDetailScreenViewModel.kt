package com.eurodental.features.taskdetails.ui

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eurodental.features.taskdetails.data.TaskDetails
import com.eurodental.features.tasks.data.TasksRepositoryBase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TaskDetailUIState(
    val isLoading : Boolean = true,
    val isError : Boolean = false,
    val errorMessage : String?= null,
    val task : TaskDetails? = null
)

@HiltViewModel
class TaskDetailScreenViewModel @Inject constructor(
    private val tasksRepository: TasksRepositoryBase,
    savedStateHandle: SavedStateHandle
) : ViewModel(){
    private val _isLoading = MutableStateFlow(true)
    private val _isError = MutableStateFlow(false)
    private val _errorMessage: MutableStateFlow<String?> = MutableStateFlow(null)
    private val _taskDetails: MutableStateFlow<TaskDetails?> = MutableStateFlow(null)

    private val taskId : Int = checkNotNull(savedStateHandle["taskId"])

    val uiState : StateFlow<TaskDetailUIState> = combine(
        _isLoading, _isError, _errorMessage, _taskDetails
    ) { isLoading, isError, errorMessage, taskDetails ->
        TaskDetailUIState(isLoading,isError,errorMessage,taskDetails)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TaskDetailUIState()
    )

    init {
        getTask()
    }

    fun getTask() {
        Log.d("TaskDetail","task id = $taskId")
        _isLoading.value = true
        viewModelScope.launch {
            val result = tasksRepository.getTaskDetails(taskId)
            _isLoading.value = false
            if(result.isSuccess) {
                _isError.value = false
                _taskDetails.value = result.getOrNull()
            }else {
                _isError.value = true
                _errorMessage.value = result.exceptionOrNull()?.message
            }
        }
    }

}