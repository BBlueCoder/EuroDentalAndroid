package com.eurodental.features.taskdetails.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eurodental.R
import com.eurodental.common.components.CircularImage
import com.eurodental.features.clients.data.models.Client
import com.eurodental.features.taskdetails.data.TaskDetails
import kotlin.math.exp

@Composable
fun TaskDetailScreen(taskId: Int, viewModel: TaskDetailScreenViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TaskDetailScreen(uiState.isError, uiState.isLoading, uiState.errorMessage, uiState.task)
}

@Composable
fun TaskDetailScreen(
    isError: Boolean = false,
    isLoading: Boolean = false,
    errorMessage: String? = null,
    task: TaskDetails?
) {
    Scaffold {
        Box(modifier = Modifier.padding(it)) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(text = "Euro Dental", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(10.dp))
                if (isError) {
                    Text(text = "Error $errorMessage")
                } else if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    if (task != null) {
                        TaskDetailCard(taskDetails = task)
                    }
                }
            }

        }
    }
}

@Composable
fun TaskDetailCard(
    taskDetails: TaskDetails
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TaskDetails(
                taskName = taskDetails.taskName,
                taskDate = taskDetails.taskDate,
                status = taskDetails.status
            )

            ClientDetailsCard(client = taskDetails.client)
        }
    }
}

@Composable
fun TaskDetails(
    taskName: String?,
    taskDate: String?,
    status: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
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
}

@Composable
fun ClientDetailsCard(client: Client) {
    var expanded by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .height(if (expanded) 190.dp else 72.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        onClick = {
            expanded = !expanded
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    CircularImage(imagePath = client.imagePath)
                    Column {
                        Text(
                            text = "${client.lastName?.capitalize(Locale.current)} ${
                                client.firstName?.capitalize(
                                    Locale.current
                                )
                            }",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "${client.city?.toUpperCase(Locale.current)}",
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
                Icon(
                    painter = painterResource(id = if (expanded) R.drawable.baseline_keyboard_arrow_up_24 else R.drawable.baseline_keyboard_arrow_down_24),
                    contentDescription = "Arrow Icon",
                    modifier = Modifier.size(28.dp)
                )
            }
            if (expanded) {
                ClientDetailsField(icon = R.drawable.baseline_email_24, text = client.email)
                ClientDetailsField(
                    icon = R.drawable.baseline_phone_android_24,
                    text = client.phoneNumber ?: "--"
                )
                ClientDetailsField(
                    icon = R.drawable.baseline_location_on_24,
                    text = client.address ?: "--"
                )
            }
        }
    }
}

@Composable
fun ClientDetailsField(icon: Int, text: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "Icon",
            modifier = Modifier.size(28.dp)
        )
        Text(text = text, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(5.dp))
    }
}