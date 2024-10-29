package com.eurodental.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.eurodental.features.home.ui.Home
import com.eurodental.features.taskdetails.ui.TaskDetailScreen

@Composable
fun RootGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = MainGraph) {
        AuthGraph(navController)

        composable<MainGraph> {
            Home(
                navigateToAuth = {
                    navController.navigate(AuthGraph) {
                        popUpTo(0){}
                    }
                },
                navigateToTaskDetails = {
                    navController.navigate(TaskDetailsScreen(taskId = it))
                }
            )
        }

        navigation<TaskDetailsGraph>(startDestination = TaskDetailsScreen(taskId = 0)) {
            composable<TaskDetailsScreen> {
                val args = it.toRoute<TaskDetailsScreen>()
                TaskDetailScreen(taskId = args.taskId)
            }
        }
    }
}