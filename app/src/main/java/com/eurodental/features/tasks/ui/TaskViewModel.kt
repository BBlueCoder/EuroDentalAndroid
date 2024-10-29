package com.eurodental.features.tasks.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eurodental.features.tasks.data.TasksRepository
import com.eurodental.features.tasks.data.models.DateTab
import com.eurodental.features.tasks.data.models.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

data class TaskUIState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val data: List<Task> = emptyList()
)

@HiltViewModel
class TaskViewModel @Inject constructor(private val repo: TasksRepository) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    private val _isError = MutableStateFlow(false)
    private val _errorMessage: MutableStateFlow<String?> = MutableStateFlow(null)
    private val _data: MutableStateFlow<List<Task>> = MutableStateFlow(emptyList())

    private val _dateTabs: MutableStateFlow<List<DateTab>> = MutableStateFlow(emptyList())
    val dateTabs: StateFlow<List<DateTab>> = _dateTabs.asStateFlow()

    private var currentSelectedDates: DateTab = DateTab(label = "")

    val uiState: StateFlow<TaskUIState> = combine(
        _isLoading, _isError, _errorMessage, _data
    ) { isLoading, isError, errorMessage, data ->
        TaskUIState(isLoading, isError, errorMessage, data)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TaskUIState()
    )

    init {
        loadDates()
        getAllTasks()
    }

    private fun getAllTasks() {
        _isLoading.value = true
        viewModelScope.launch {
            val res = repo.getAllTasks(
                currentSelectedDates.exactDate,
                currentSelectedDates.dateStart,
                currentSelectedDates.dateEnd
            )
            _isLoading.value = false
            if (res.isSuccess) {
                _isError.value = false
                _data.value = res.getOrNull() ?: emptyList()
            } else {
                _isError.value = true
                _errorMessage.value = res.exceptionOrNull()?.message
            }
        }
    }

    private fun loadDates() {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dates = mutableListOf<DateTab>()
        val currentDate = LocalDate.now()
        val beforeYesterday = currentDate.minusDays(2).format(formatter)
        val yesterday = currentDate.minusDays(1).format(formatter)
        val today = currentDate.format(formatter)
        val tomorrow = currentDate.plusDays(1).format(formatter)
        val afterTomorrow = currentDate.plusDays(2).format(formatter)
        dates.add(DateTab(exactDate = beforeYesterday, label = beforeYesterday))
        dates.add(DateTab(exactDate = yesterday, label = "Yesterday"))
        dates.add(DateTab(exactDate = today, label = "Today"))
        dates.add(DateTab(exactDate = tomorrow, label = "Tomorrow"))
        dates.add(DateTab(exactDate = afterTomorrow, label = afterTomorrow))


        _dateTabs.value = dates
        currentSelectedDates = currentSelectedDates.copy(exactDate = today.format(formatter))
    }

    fun setDate(date: DateTab) {
        currentSelectedDates = date
        Log.d("DateTab", "date = $date")
        getAllTasks()
    }

    fun setDates(startDate: Long?, endDate: Long?) {
        if (startDate == null && endDate == null)
            return

        var startDateString: String? = null
        var endDateString: String? = null

        if (startDate != null) {
            startDateString = convertDateInMillisToDateWithFormat(startDate)
        }

        if (endDate != null) {
            endDateString = convertDateInMillisToDateWithFormat(endDate)
        }

        Log.d("Date","start date $startDateString end date $endDateString")

        if (startDateString != null && endDateString != null) {
            val _currentSelectedDate = if (startDateString != endDateString)
                DateTab(dateStart = startDateString, dateEnd = endDateString, label = "")
            else
                DateTab(exactDate = startDateString, label = "")
            setDate(_currentSelectedDate)
            return
        }

        if (startDateString != null) {
            val _currentSelectedDate = DateTab(exactDate = startDateString, label = "")
            setDate(_currentSelectedDate)
        }

        if (endDateString != null) {
            val _currentSelectedDate = DateTab(exactDate = endDateString, label = "")
            setDate(_currentSelectedDate)
        }
    }

    private fun convertDateInMillisToDateWithFormat(date: Long): String? {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val dateObject = LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault())
        return dateObject.format(formatter)
    }
}