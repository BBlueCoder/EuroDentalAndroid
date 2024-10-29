package com.eurodental.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.eurodental.features.clients.ui.ClientScreen
import com.eurodental.features.stocks.ui.StockScreen
import com.eurodental.features.tasks.ui.TaskScreen

@Composable
fun MainGraph(
    navController: NavHostController,
    navigateToTaskDetails : (taskId : Int) -> Unit,
) {
    NavHost(navController = navController, startDestination = TaskScreen ) {
        composable<TaskScreen> {
            TaskScreen(navHostController = navController,navigateToTaskDetails = navigateToTaskDetails)
        }
        composable<StockScreen> {
            StockScreen(navHostController = navController)
        }
        composable<ClientScreen> {
            ClientScreen(navHostController = navController)
        }
    }
}