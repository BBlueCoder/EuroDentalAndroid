package com.eurodental.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.eurodental.features.auth.ui.LoginScreen

fun NavGraphBuilder.AuthGraph(
    navController: NavHostController
) {
    navigation<AuthGraph>(startDestination = LoginScreen) {
        composable<LoginScreen> {
           LoginScreen(navHostController = navController)
        }
    }
}