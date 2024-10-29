package com.eurodental.navigation

import kotlinx.serialization.Serializable

// Auth Graph
@Serializable
object AuthGraph

@Serializable
object LoginScreen

// Main Graph
@Serializable
object MainGraph

@Serializable
object TaskScreen

@Serializable
object StockScreen

@Serializable
object ClientScreen

// Task Details Graph
@Serializable
object TaskDetailsGraph

@Serializable
data class TaskDetailsScreen(
    val taskId : Int
)



