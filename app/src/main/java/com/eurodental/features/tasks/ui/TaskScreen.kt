package com.eurodental.features.tasks.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.eurodental.R
import com.eurodental.features.tasks.data.models.DateTab
import com.eurodental.features.tasks.data.models.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    navHostController: NavHostController,
    navigateToTaskDetails : (taskId : Int) -> Unit,
    viewModel: TaskViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val datesData by viewModel.dateTabs.collectAsStateWithLifecycle()
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(2)
    }

    var showDateDialog by remember {
        mutableStateOf(false)
    }

    val datePickerState = rememberDateRangePickerState()


    TaskScreen(
        isLoading = uiState.isLoading,
        isError = uiState.isError,
        data = uiState.data,
        selectedTabIndex = selectedTabIndex,
        dateTabs = datesData,
        onTabClick = { idx, dateTab ->
            selectedTabIndex = idx
            viewModel.setDate(dateTab)
        },
        dateState = datePickerState,
        showDateDialog = showDateDialog,
        onDateClick = {
            showDateDialog = true
        },
        onConfirm = { dates ->
            showDateDialog = false
            viewModel.setDates(dates.first,dates.second)
        },
        onDismiss = {
            showDateDialog = false
        },
        navigateToTaskDetails = navigateToTaskDetails
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    isLoading : Boolean,
    isError: Boolean,
    data: List<Task>,
    selectedTabIndex: Int,
    dateTabs: List<DateTab>,
    onTabClick: (index: Int, dateTab: DateTab) -> Unit,
    dateState: DateRangePickerState,
    showDateDialog: Boolean,
    onDateClick: () -> Unit,
    onConfirm: (Pair<Long?, Long?>) -> Unit,
    onDismiss: () -> Unit,
    navigateToTaskDetails : (taskId : Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        TopComponent(
            isLoading = isLoading,
            selectedTabIndex = selectedTabIndex,
            tabsData = dateTabs,
            onTabClick = onTabClick,
            dateState = dateState,
            showDateDialog = showDateDialog,
            onDateClick = onDateClick,
            onConfirm = onConfirm,
            onDismiss = onDismiss
        )

        Spacer(modifier = Modifier.height(10.dp))
        if (isError) {
            Text(text = "Error!")
        } else {
            if(!isLoading) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(data) { task ->
                        TaskCard(
                            taskId = task.id,
                            taskName = task.taskName,
                            statusColor = if (task.status == "In Progress") Color.Green else Color.Red,
                            taskDate = task.taskDate,
                            clientImage = task.clientImage,
                            navigateToTaskDetails = navigateToTaskDetails
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComp(
    dateState: DateRangePickerState,
    onConfirm: (Pair<Long?, Long?>) -> Unit,
    onDismiss: () -> Unit
) {
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onConfirm(
                    Pair(
                        dateState.selectedStartDateMillis,
                        dateState.selectedEndDateMillis
                    )
                )
            }) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }) {
        DateRangePicker(
            state = dateState,
            showModeToggle = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopComponent(
    isLoading: Boolean,
    selectedTabIndex: Int,
    tabsData: List<DateTab>,
    onTabClick: (index: Int, dateTab : DateTab) -> Unit,
    dateState: DateRangePickerState,
    showDateDialog: Boolean,
    onDateClick: () -> Unit,
    onConfirm: (Pair<Long?, Long?>) -> Unit,
    onDismiss: () -> Unit,
) {
    Column {
        TopBar(
            dateState = dateState,
            showDateDialog = showDateDialog,
            onDateClick = onDateClick,
            onConfirm = onConfirm,
            onDismiss = onDismiss
        )
        if(isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
        DateTabs(selectedTabIndex = selectedTabIndex, tabsData = tabsData, onTabClick = onTabClick)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    dateState: DateRangePickerState,
    showDateDialog: Boolean,
    onDateClick: () -> Unit,
    onConfirm: (Pair<Long?, Long?>) -> Unit,
    onDismiss: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Euro Dental", style = MaterialTheme.typography.headlineMedium)
        IconButton(onClick = onDateClick) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_date_range_24),
                contentDescription = "Date Picker"
            )
        }
        if (showDateDialog) {
            DatePickerComp(dateState = dateState, onConfirm = onConfirm, onDismiss = onDismiss)
        }
    }
}

@Composable
fun DateTabs(
    selectedTabIndex: Int,
    tabsData: List<DateTab>,
    onTabClick: (index: Int, dateTab : DateTab) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        divider = {
            Spacer(modifier = Modifier.height(0.dp))
        },
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        tabsData.forEachIndexed { idx, tabData ->
            Tab(selected = selectedTabIndex == idx, modifier = Modifier.size(40.dp), onClick = {
                onTabClick(idx,tabData)
            }) {
                Text(text = tabData.label, style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

@Composable
fun TaskCard(
    taskId: Int,
    taskName: String?,
    statusColor: Color,
    taskDate: String?,
    clientImage: String?,
    navigateToTaskDetails : (taskId : Int) -> Unit,
) {
    OutlinedCard(
//        elevation = CardDefaults.cardElevation(
//            defaultElevation = 6.dp
//        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        onClick = {
            navigateToTaskDetails(taskId)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(5.dp)
                    .background(color = statusColor)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(2f)
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Text(
                    text = taskName ?: "Task --",
                    style = MaterialTheme.typography.titleLarge,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = taskDate ?: "",
                    style = MaterialTheme.typography.titleSmall,
                )
            }
//            if (task.clientImage == null) {
//                Image(
//                    painter = painterResource(id = R.drawable.baseline_person_24),
//                    contentDescription = "client icon",
//                    modifier = Modifier
//                        .size(width = 38.dp, height = 38.dp))
//            } else {
            AsyncImage(
                model = clientImage,
                contentDescription = "Image of client",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.baseline_person_24),
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
            )
//            }
        }
    }
}